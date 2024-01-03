package com.ewentteamfsm.features.viewAllOrder.orderOptimized

import com.ewentteamfsm.app.domain.ProductOnlineRateTempEntity
import com.ewentteamfsm.base.BaseResponse
import com.ewentteamfsm.features.login.model.productlistmodel.ProductRateDataModel
import java.io.Serializable

class ProductRateOnlineListResponseModel: BaseResponse(), Serializable {
    var product_rate_list: ArrayList<ProductOnlineRateTempEntity>? = null
}