package com.ewentteamfsm.features.login.model.productlistmodel

import com.ewentteamfsm.app.domain.ProductListEntity
import com.ewentteamfsm.base.BaseResponse

/**
 * Created by Saikat on 20-11-2018.
 */
class ProductListResponseModel : BaseResponse() {
    //var product_list: ArrayList<ProductListDataModel>? = null
    var product_list: ArrayList<ProductListEntity>? = null
}