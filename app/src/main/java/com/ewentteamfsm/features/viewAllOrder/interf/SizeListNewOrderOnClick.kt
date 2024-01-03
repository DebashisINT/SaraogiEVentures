package com.ewentteamfsm.features.viewAllOrder.interf

import com.ewentteamfsm.app.domain.NewOrderProductEntity
import com.ewentteamfsm.app.domain.NewOrderSizeEntity

interface SizeListNewOrderOnClick {
    fun sizeListOnClick(size: NewOrderSizeEntity)
}