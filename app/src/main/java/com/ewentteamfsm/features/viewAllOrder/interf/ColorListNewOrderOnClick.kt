package com.ewentteamfsm.features.viewAllOrder.interf

import com.ewentteamfsm.app.domain.NewOrderColorEntity
import com.ewentteamfsm.app.domain.NewOrderProductEntity

interface ColorListNewOrderOnClick {
    fun productListOnClick(color: NewOrderColorEntity)
}