package com.ewentteamfsm.features.damageProduct.api

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.ewentteamfsm.app.FileUtils
import com.ewentteamfsm.base.BaseResponse
import com.ewentteamfsm.features.NewQuotation.model.*
import com.ewentteamfsm.features.addshop.model.AddShopRequestData
import com.ewentteamfsm.features.addshop.model.AddShopResponse
import com.ewentteamfsm.features.damageProduct.model.DamageProductResponseModel
import com.ewentteamfsm.features.damageProduct.model.delBreakageReq
import com.ewentteamfsm.features.damageProduct.model.viewAllBreakageReq
import com.ewentteamfsm.features.login.model.userconfig.UserConfigResponseModel
import com.ewentteamfsm.features.myjobs.model.WIPImageSubmit
import com.ewentteamfsm.features.photoReg.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class GetDamageProductListRegRepository(val apiService : GetDamageProductListApi) {

    fun viewBreakage(req: viewAllBreakageReq): Observable<DamageProductResponseModel> {
        return apiService.viewBreakage(req)
    }

    fun delBreakage(req: delBreakageReq): Observable<BaseResponse>{
        return apiService.BreakageDel(req.user_id!!,req.breakage_number!!,req.session_token!!)
    }

}