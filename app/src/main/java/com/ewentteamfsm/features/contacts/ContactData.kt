package com.ewentteamfsm.features.contacts

import com.ewentteamfsm.app.domain.CallHisEntity
import com.ewentteamfsm.app.domain.CompanyMasterEntity
import com.ewentteamfsm.app.domain.SourceMasterEntity
import com.ewentteamfsm.app.domain.StageMasterEntity
import com.ewentteamfsm.app.domain.StatusMasterEntity
import com.ewentteamfsm.app.domain.TeamListEntity
import com.ewentteamfsm.app.domain.TypeMasterEntity
import com.ewentteamfsm.base.BaseResponse

data class ContactGr(var gr_id:String="",var gr_name:String="")
data class ContactDtls(val gr_name:String="", val name:String="", val number:String="", var isTick:Boolean = false)

data class ContactMasterRes(var company_list:ArrayList<CompanyMasterEntity>):BaseResponse()
data class TypeMasterRes(var type_list:ArrayList<TypeMasterEntity>):BaseResponse()
data class StatusMasterRes(var status_list:ArrayList<StatusMasterEntity>):BaseResponse()
data class TeamListRes(var member_list:ArrayList<TeamListEntity>):BaseResponse()


data class CustomData(var id:String="",var name:String="")

data class SourceMasterRes(var source_list:ArrayList<SourceMasterEntity>):BaseResponse()
data class StageMasterRes(var stage_list:ArrayList<StageMasterEntity>):BaseResponse()

data class CallHisDtls(var user_id:String="",var call_his_list:ArrayList<CallHisEntity> = ArrayList()) :BaseResponse()


