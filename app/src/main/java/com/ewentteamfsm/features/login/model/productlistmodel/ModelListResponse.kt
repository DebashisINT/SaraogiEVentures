package com.ewentteamfsm.features.login.model.productlistmodel

import com.ewentteamfsm.app.domain.ModelEntity
import com.ewentteamfsm.app.domain.ProductListEntity
import com.ewentteamfsm.base.BaseResponse

class ModelListResponse: BaseResponse() {
    var model_list: ArrayList<ModelEntity>? = null
}