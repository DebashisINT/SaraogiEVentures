package com.ewentteamfsm.features.orderList.model

import com.ewentteamfsm.base.BaseResponse


class ReturnListResponseModel: BaseResponse() {
    var return_list: ArrayList<ReturnDataModel>? = null
}