package com.ewentteamfsm.features.viewAllOrder.model

import com.ewentteamfsm.app.domain.NewOrderColorEntity
import com.ewentteamfsm.app.domain.NewOrderGenderEntity
import com.ewentteamfsm.app.domain.NewOrderProductEntity
import com.ewentteamfsm.app.domain.NewOrderSizeEntity
import com.ewentteamfsm.features.stockCompetetorStock.model.CompetetorStockGetDataDtls

class NewOrderDataModel {
    var status:String ? = null
    var message:String ? = null
    var Gender_list :ArrayList<NewOrderGenderEntity>? = null
    var Product_list :ArrayList<NewOrderProductEntity>? = null
    var Color_list :ArrayList<NewOrderColorEntity>? = null
    var size_list :ArrayList<NewOrderSizeEntity>? = null
}

