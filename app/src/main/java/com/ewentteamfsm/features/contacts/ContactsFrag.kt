package com.ewentteamfsm.features.contacts

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ewentteamfsm.R
import com.ewentteamfsm.app.AppDatabase
import com.ewentteamfsm.app.Pref
import com.ewentteamfsm.app.domain.AddShopDBModelEntity
import com.ewentteamfsm.app.domain.CallHisEntity
import com.ewentteamfsm.app.types.FragType
import com.ewentteamfsm.app.uiaction.IntentActionable
import com.ewentteamfsm.app.utils.AppUtils
import com.ewentteamfsm.app.utils.PermissionUtils
import com.ewentteamfsm.app.utils.Toaster
import com.ewentteamfsm.app.widgets.MovableFloatingActionButton
import com.ewentteamfsm.base.presentation.BaseFragment
import com.ewentteamfsm.features.dashboard.presentation.DashboardActivity
import com.ewentteamfsm.features.location.LocationWizard
import com.ewentteamfsm.features.location.SingleShotLocationProvider
import com.ewentteamfsm.features.nearbyshops.presentation.AdapterCallLogL
import com.ewentteamfsm.widgets.AppCustomEditText
import com.pnikosis.materialishprogress.ProgressWheel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import timber.log.Timber
import java.util.Locale
import java.util.Random


class ContactsFrag : BaseFragment(), View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var mFab: MovableFloatingActionButton
    private lateinit var tvNodata: TextView
    private lateinit var ivContactSync: ImageView
    private lateinit var ivContactSyncDel: ImageView
    private lateinit var adapterContGr:AdapterContactGr
    private lateinit var adapterContName:AdapterContactName
    private lateinit var progress_wheel: ProgressWheel
    private lateinit var rvContactL: RecyclerView

    private lateinit var et_search: AppCustomEditText
    private lateinit var iv_search: ImageView
    private lateinit var iv_mic: ImageView

    private var locationStr_lat:String = ""
    private var locationStr_long:String = ""
    private var locationStr:String = ""
    private var location_pinStr:String = ""

    private lateinit var adapterContactList: AdapterContactList

    private var permissionUtils: PermissionUtils? = null
    private var contGrDialog: Dialog? = null

    private lateinit var adapterCallLogL : AdapterCallLogL

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.frag_contacts, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        progress_wheel = view.findViewById(R.id.progress_wheel_frag_add_cont)
        tvNodata = view.findViewById(R.id.tv_frag_add_cont_noData)
        rvContactL = view.findViewById(R.id.rv_frag_contacts_list)
        mFab = view.findViewById(R.id.fab_frag_contacts_add_contacts)
        ivContactSync = view.findViewById(R.id.iv_frag_contacts_dialog)
        ivContactSyncDel = view.findViewById(R.id.iv_frag_contacts_dialog_del)

        iv_mic = view.findViewById(R.id.iv_frag_contacts_mic)
        et_search = view.findViewById(R.id.et_frag_contacts_search)
        iv_search = view.findViewById(R.id.iv_frag_contacts_search)

        mFab.setOnClickListener(this)
        ivContactSync.setOnClickListener(this)
        ivContactSyncDel.setOnClickListener(this)
        iv_search.setOnClickListener(this)
        iv_mic.setOnClickListener(this)
        mFab.setCustomClickListener {
            (mContext as DashboardActivity).loadFragment(FragType.ContactsAddFrag, true, "")
        }
        initPermissionCheckOne()
        if(AppUtils.isOnline(mContext)){
            singleLocation()
        }else{
            locationStr = LocationWizard.getNewLocationName(mContext, Pref.current_latitude.toDouble(), Pref.current_longitude.toDouble())
            location_pinStr = LocationWizard.getPostalCode(mContext, Pref.current_latitude.toDouble(), Pref.current_longitude.toDouble())
            locationStr_lat =Pref.current_latitude.toDouble().toString()
            locationStr_long =Pref.current_longitude.toDouble().toString()
            progress_wheel.stopSpinning()
        }
        shopContactList("")

      /*  button.setOnClickListener(View.OnClickListener {
            TextHighlighter()
                .setBackgroundColor(Color.parseColor("#FFFF00"))
                .setForegroundColor(Color.GREEN)
                .addTarget(textView)
                .highlight(editText.getText().toString(), TextHighlighter.BASE_MATCHER)
        })*/

        /*et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length != 0){
                    shopContactList(p0.toString())
                }
            }
        })*/
    }

    fun initPermissionCheckOne() {
        var permissionList = arrayOf<String>( Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.READ_CONTACTS)
        permissionUtils = PermissionUtils(mContext as Activity, object : PermissionUtils.OnPermissionListener {
            @TargetApi(Build.VERSION_CODES.M)
            override fun onPermissionGranted() {

            }
            override fun onPermissionNotGranted() {

            }
        },permissionList)
    }



    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_frag_contacts_dialog_del->{
                AppDatabase.getDBInstance()!!.addShopEntryDao().del99()
                shopContactList("")
            }
            R.id.iv_frag_contacts_dialog -> {
                contGrDialog = Dialog(mContext)
                contGrDialog!!.setCancelable(true)
                contGrDialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                contGrDialog!!.setContentView(R.layout.dialog_contact_gr)
                val tvHeader = contGrDialog!!.findViewById(R.id.dialog_contact_gr_header) as TextView
                val rvContactGrName = contGrDialog!!.findViewById(R.id.rv_dialog_cont_gr) as RecyclerView
                val iv_close = contGrDialog!!.findViewById(R.id.iv_dialog_generic_list_close_icon) as ImageView

                tvHeader.text = "Select contact group"
                contGrDialog!!.show()

                doAsync {
                    progress_wheel.spin()
                    var grNameL = AppUtils.getPhoneBookGroups(mContext) as ArrayList<ContactGr>
                    uiThread {
                        progress_wheel.stopSpinning()
                        if(grNameL.size>0){
                            adapterContGr = AdapterContactGr(mContext,grNameL,object :AdapterContactGr.onClick
                            {
                                override fun onGrClick(obj: ContactGr) {
                                    // contGrDialog.dismiss()
                                    showContactNameL(obj)
                                }
                            })
                            rvContactGrName.adapter = adapterContGr
                        }
                    }
                }



                iv_close.setOnClickListener {
                    contGrDialog!!.dismiss()
                    progress_wheel.stopSpinning()
                }
            }
            R.id.iv_frag_contacts_search->{
                shopContactList(et_search.text.toString())
            }
            R.id.iv_frag_contacts_mic->{
                progress_wheel.spin()
                val intent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                Handler().postDelayed(Runnable {
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"hi")
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?")
                }, 1000)
                try {
                    startActivityForResult(intent, 7009)
                    Handler().postDelayed(Runnable {
                        progress_wheel.stopSpinning()
                    }, 3000)

                } catch (a: ActivityNotFoundException) {
                    a.printStackTrace()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 7009){
            try {
                val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                et_search.setText(result!![0].toString())
            }catch (ex:Exception){
                ex.printStackTrace()
            }
        }
    }

    private fun singleLocation() {
        try{
            progress_wheel.spin()
            SingleShotLocationProvider.requestSingleUpdate(mContext,
                object : SingleShotLocationProvider.LocationCallback {
                    override fun onStatusChanged(status: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onProviderEnabled(status: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onProviderDisabled(status: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onNewLocationAvailable(location: Location) {
                        if(location!=null){
                            locationStr = LocationWizard.getNewLocationName(mContext, location.latitude, location.longitude)
                            location_pinStr = LocationWizard.getPostalCode(mContext, location.latitude, location.longitude)
                            locationStr_lat =location.latitude.toString()
                            locationStr_long =location.longitude.toString()
                        } else{
                            var lloc: Location = Location("")
                            lloc.latitude=Pref.current_latitude.toDouble()
                            lloc.longitude=Pref.current_longitude.toDouble()
                            locationStr_lat =lloc.latitude.toString()
                            locationStr_long =lloc.longitude.toString()
                            locationStr = LocationWizard.getNewLocationName(mContext, lloc.latitude, lloc.longitude)
                            location_pinStr = LocationWizard.getPostalCode(mContext, lloc.latitude, lloc.longitude)
                        }
                        progress_wheel.stopSpinning()
                    }

                })
        }catch (ex:Exception){
            ex.printStackTrace()
            progress_wheel.stopSpinning()
        }
    }

    fun showContactNameL(obj:ContactGr){
        doAsync {
            progress_wheel.spin()
            var contactL : ArrayList<ContactDtls> = ArrayList()
            contactL = AppUtils.getContactsFormGroup(obj.gr_id,obj.gr_name,mContext)
            uiThread {
                progress_wheel.stopSpinning()
                if(contactL.size>0){
                    var contactLTemp : ArrayList<ContactDtls> = contactL.clone() as ArrayList<ContactDtls>
                    //contactLTemp.addAll(contactL)

                    val contactDialog = Dialog(mContext)
                    contactDialog.setCancelable(true)
                    contactDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    contactDialog.setContentView(R.layout.dialog_cont_select)
                    val rvContactL = contactDialog.findViewById(R.id.rv_dialog_cont_list) as RecyclerView
                    val tvHeader = contactDialog.findViewById(R.id.tv_dialog_cont_sel_header) as TextView
                    val submit = contactDialog.findViewById(R.id.tv_dialog_cont_list_submit) as TextView
                    val et_contactNameSearch = contactDialog.findViewById(R.id.et_dialog_contact_search) as AppCustomEditText
                    val cb_selectAll = contactDialog.findViewById(R.id.cb_dialog_cont_select_all) as CheckBox
                    val iv_close = contactDialog.findViewById(R.id.iv_dialog_generic_list_close_icon) as ImageView
                    tvHeader.text = "Select Contact(s)"


                    iv_close.setOnClickListener {
                        contactDialog.dismiss()
                        progress_wheel.stopSpinning()
                    }

                    for(i in 0..contactL.size-1){
                        var ob = contactL.get(i)
                        var  isPresentInDb= (AppDatabase.getDBInstance()!!.addShopEntryDao().getCustomData(ob.name,ob.number) as ArrayList<AddShopDBModelEntity>).size
                        if(isPresentInDb!=0){
                            contactLTemp.remove(ob)
                        }
                    }

                    if(contactLTemp.size>0){
                        var finalL : ArrayList<ContactDtls> = ArrayList()
                        try {
                            if(contactLTemp.size>2){
                                finalL = contactLTemp.sortedByDescending { it.name }.reversed() as ArrayList<ContactDtls>
                            }else{
                                finalL = contactLTemp.clone() as ArrayList<ContactDtls>
                            }
                        }catch (ex:Exception){
                            ex.printStackTrace()
                        }
                        var contactTickL : ArrayList<ContactDtls> = ArrayList()
                        //rvContactL.layoutManager = LinearLayoutManager(mContext)
                        adapterContName = AdapterContactName(mContext,finalL,object :AdapterContactName.onClick{
                            override fun onTickUntick(obj: ContactDtls, isTick: Boolean) {
                                if(isTick){
                                    contactTickL.add(obj)
                                    finalL.filter { it.name.equals(obj.name) }.first().isTick = true
                                    tvHeader.text = "Selected Contact(s) : (${contactTickL.size})"


                                }else{
                                    contactTickL.remove(obj)
                                    finalL.filter { it.name.equals(obj.name) }.first().isTick = false
                                    tvHeader.text = "Selected Contact(s) : (${contactTickL.size})"

                                }
                            }
                        },{
                            it
                        })
                        /*   cb_selectAll.setOnClickListener {
                               adapterContName.selectAll()
                           }*/

                        cb_selectAll.setOnCheckedChangeListener { compoundButton, b ->

                            if (compoundButton.isChecked) {
                                adapterContName.selectAll()
                                cb_selectAll.setText("Deselect All")
                            }
                            else{

                                adapterContName.deselectAll()
                                cb_selectAll.setText("Select All")

                            }
                        }

                        et_contactNameSearch.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(p0: Editable?) {
                            }

                            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            }

                            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                                adapterContName!!.getFilter().filter(et_contactNameSearch.text.toString().trim())
                            }
                        })

                        submit.setOnClickListener {
                            if(contactTickL.size>0){
                                contactDialog.dismiss()
                                contGrDialog!!.dismiss()
                                submitContact(contactTickL)
                            }else{
                                contactDialog.dismiss()
                                contGrDialog!!.dismiss()
                                Toaster.msgShort(mContext,"Select Contact(s)")
                            }
                        }
                        rvContactL.adapter = adapterContName
                        contactDialog.show()
                        //contGrDialog!!.dismiss()

                    }else{
                        Toaster.msgShort(mContext,"No contact avaliable")
                    }
                }
            }
        }
    }


    fun submitContact(contactTickL : ArrayList<ContactDtls>){
        doAsync {
            progress_wheel.spin()
            for(i in 0..contactTickL.size-1){
                var shopObj:AddShopDBModelEntity = AddShopDBModelEntity()
                val random = Random()
                shopObj.shop_id = Pref.user_id + "_" + System.currentTimeMillis().toString() +  (random.nextInt(999 - 100) + 100).toString()
                shopObj.shopName = contactTickL.get(i).name
                shopObj.ownerName = contactTickL.get(i).name
                shopObj.companyName_id = ""
                shopObj.companyName = ""
                shopObj.jobTitle = ""
                shopObj.ownerEmailId = ""
                shopObj.ownerContactNumber = contactTickL.get(i).number
                shopObj.address = locationStr
                shopObj.pinCode = location_pinStr
                shopObj.shopLat = locationStr_lat.toDouble()
                shopObj.shopLong = locationStr_long.toDouble()
                shopObj.crm_assignTo = Pref.user_name.toString()
                shopObj.crm_assignTo_ID = Pref.user_id
                shopObj.crm_type = ""
                shopObj.crm_type_ID = ""
                shopObj.crm_status=""
                shopObj.crm_status_ID=""
                shopObj.crm_source=""
                shopObj.crm_source_ID=""
                shopObj.crm_reference=""
                shopObj.crm_reference_ID=""
                shopObj.crm_reference_ID_type=""
                shopObj.remarks = ""
                shopObj.amount = ""
                shopObj.crm_stage=""
                shopObj.crm_stage_ID=""
                shopObj.crm_reference = ""
                shopObj.crm_reference_ID = ""
                shopObj.type = "99"
                shopObj.added_date = AppUtils.getCurrentDateTime()
                shopObj.crm_saved_from = "Phone Book"
                shopObj.isUploaded = true
                AppDatabase.getDBInstance()!!.addShopEntryDao().insert(shopObj)
            }
            uiThread {
                Handler().postDelayed(Runnable {
                    progress_wheel.stopSpinning()
                    voiceMsg("Contact added successfully")
                    Toaster.msgShort(mContext,"Contact added successfully.")
                    shopContactList("")
                }, 1500)
            }
        }
    }

    fun shopContactList(searchObj:String){
        var contL = AppDatabase.getDBInstance()!!.addShopEntryDao().getContatcShops() as ArrayList<AddShopDBModelEntity>
        if(!searchObj.equals("")){
            var searchL = contL.filter { it.shopName.contains(searchObj, ignoreCase = true) || it.ownerContactNumber.contains(searchObj, ignoreCase = true) ||
                    it.crm_stage.contains(searchObj, ignoreCase = true) || it.crm_source.contains(searchObj, ignoreCase = true) ||
                    it.crm_status.contains(searchObj, ignoreCase = true) || it.crm_type.contains(searchObj, ignoreCase = true) || it.crm_saved_from.contains(searchObj, ignoreCase = true)} as ArrayList<AddShopDBModelEntity>
            contL = searchL
        }
        if(contL.size>0){
            tvNodata.visibility = View.GONE
            (mContext as DashboardActivity).setTopBarTitle("Contact(s) : ${contL.size}")
            adapterContactList = AdapterContactList(mContext,contL,et_search.text.toString(),object :AdapterContactList.onClick{
                override fun onCallClick(obj: AddShopDBModelEntity) {
                    IntentActionable.initiatePhoneCall(mContext, obj.ownerContactNumber)
                }

                override fun onWhatsClick(obj: AddShopDBModelEntity) {
                    val url = "https://api.whatsapp.com/send?phone=+91${obj.ownerContactNumber}"
                    try {
                        val pm = mContext.packageManager
                        pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)
                    } catch (e: PackageManager.NameNotFoundException ) {
                        e.printStackTrace()
                        (mContext as DashboardActivity).showSnackMessage("Whatsapp app not installed in your phone.")
                    }
                    catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        (mContext as DashboardActivity).showSnackMessage("This is not whatsApp no.")
                    }
                }

                override fun onEmailClick(obj: AddShopDBModelEntity) {
                    IntentActionable.sendMail(mContext, obj.ownerEmailId, "")
                }

                override fun onInfoClick(obj: AddShopDBModelEntity) {
                    saveCallHisToDB(obj)
                }
            })
            rvContactL.adapter = adapterContactList
            rvContactL.visibility = View.VISIBLE
        }else{
            (mContext as DashboardActivity).setTopBarTitle("Contact(s)")
            tvNodata.visibility = View.VISIBLE
            rvContactL.visibility = View.GONE
        }
    }

    private fun voiceMsg(msg: String) {
        if (Pref.isVoiceEnabledForAttendanceSubmit) {
            val speechStatus = (mContext as DashboardActivity).textToSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null)
            if (speechStatus == TextToSpeech.ERROR)
                Log.e("Add Day Start", "TTS error in converting Text to Speech!");
        }
    }

    fun saveCallHisToDB(obj:AddShopDBModelEntity){
        try{
            println("cont_frag call his saveCallHisToDB")
            progress_wheel.spin()
            doAsync {
                //var callHisL = AppUtils.obtenerDetallesLlamadas(mContext) as java.util.ArrayList<AppUtils.Companion.PhoneCallDtls>
                var callHisL = AppUtils.obtenerDetallesLlamadasByNumber(mContext,obj.ownerContactNumber) as java.util.ArrayList<AppUtils.Companion.PhoneCallDtls>
                println("cont_frag call his size ${callHisL.size}")
                if(callHisL.size>0){
                    for(i in 0..callHisL.size-1){
                        try{
                            var obj: CallHisEntity = CallHisEntity()
                            var callNo = if(callHisL.get(i).number!!.length>10) callHisL.get(i).number!!.replace("+","").removeRange(0,2) else callHisL.get(i).number!!
                            var isMyShop = AppDatabase.getDBInstance()!!.addShopEntryDao().getShopByPhone(callNo) as ArrayList<AddShopDBModelEntity>
                            if(isMyShop.size>0){
                                obj.apply {
                                    shop_id = isMyShop.get(0).shop_id.toString()
                                    call_number = callNo
                                    call_date = callHisL.get(i).callDateTime!!.split(" ").get(0)
                                    call_time = callHisL.get(i).callDateTime!!.split(" ").get(1)
                                    call_date_time = callHisL.get(i).callDateTime!!
                                    call_type = callHisL.get(i).type!!
                                    if(call_type.equals("MISSED",ignoreCase = true)){
                                        call_duration_sec = "0"
                                    }else{
                                        call_duration_sec = callHisL.get(i).callDuration!!
                                    }
                                    call_duration = AppUtils.getMMSSfromSeconds(call_duration_sec.toInt())
                                }
                                var isPresent = (AppDatabase.getDBInstance()!!.callhisDao().getFilterData(obj.call_number,obj.call_date,obj.call_time,obj.call_type,obj.call_duration_sec) as ArrayList<CallHisEntity>).size
                                if(isPresent==0){
                                    println("cont_frag call his insert ${obj.call_number}")
                                    Timber.d("tag_log_insert ${obj.call_number} ${obj.call_duration}")
                                    AppDatabase.getDBInstance()!!.callhisDao().insert(obj)
                                }
                            }
                        }catch (ex:Exception){
                            ex.printStackTrace()
                            println("cont_frag call his err inner ${ex.message}")
                        }
                    }
                }
                uiThread {
                    progress_wheel.stopSpinning()
                    showCallInfo(obj)
                }
            }
        }catch (ex:Exception){
            ex.printStackTrace()
            println("cont_frag call his err ${ex.message}")
        }
    }

    fun showCallInfo(obj:AddShopDBModelEntity){
        val simpleDialog = Dialog(mContext)
        simpleDialog.setCancelable(false)
        simpleDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        simpleDialog.setContentView(R.layout.dialog_info_1)
        val dialogHeader = simpleDialog.findViewById(R.id.tv_dialog_info_1_header) as TextView
        val rvList = simpleDialog.findViewById(R.id.rv_dialog_info_1_info) as RecyclerView
        val dialogCross = simpleDialog.findViewById(R.id.iv_dialog_info_1_cross) as ImageView
        val tv_noData = simpleDialog.findViewById(R.id.tv_dialog_info_1_info_na) as TextView
        val tv_totalCallDuration = simpleDialog.findViewById(R.id.tv_dialog_info_1_info_total_call_duraton) as TextView
        val tv_totalCallCount = simpleDialog.findViewById(R.id.tv_dialog_info_1_info_total_call_count) as TextView
        val ll_countRoot = simpleDialog.findViewById(R.id.ll_dialog_info_1_count_root) as LinearLayout

        dialogHeader.text = AppUtils.hiFirstNameText()

        dialogCross.setOnClickListener({ view ->
            simpleDialog.cancel()
        })
        simpleDialog.show()

        var callL = AppDatabase.getDBInstance()!!.callhisDao().getCallListByPhone(obj.ownerContactNumber!!) as ArrayList<CallHisEntity>
        //var callL = AppDatabase.getDBInstance()!!.callhisDao().getCallListByPhone("9830916971") as ArrayList<CallHisEntity>
        if(callL.size>0){
            tv_noData.visibility = View.GONE
            rvList.visibility = View.VISIBLE
            ll_countRoot.visibility = View.VISIBLE
            adapterCallLogL = AdapterCallLogL(mContext,callL,false,object :AdapterCallLogL.onClick{
                override fun onSyncClick(obj: CallHisEntity) {

                }
            })
            rvList.adapter=adapterCallLogL

            var totalCallDurationInSec: Int = callL.sumOf { it.call_duration_sec.toInt() }
            tv_totalCallDuration.text = "Total Call duration : ${AppUtils.getMMSSfromSeconds(totalCallDurationInSec.toInt())}"
            tv_totalCallCount.text = "Total Call count : ${callL.size}"

        }else{
            tv_noData.visibility = View.VISIBLE
            rvList.visibility = View.GONE
            ll_countRoot.visibility = View.GONE
        }
    }


}