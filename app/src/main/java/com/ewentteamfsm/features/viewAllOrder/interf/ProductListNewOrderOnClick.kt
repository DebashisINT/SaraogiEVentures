package com.ewentteamfsm.features.viewAllOrder.interf

import com.ewentteamfsm.app.domain.NewOrderGenderEntity
import com.ewentteamfsm.app.domain.NewOrderProductEntity

interface ProductListNewOrderOnClick {
    fun productListOnClick(product: NewOrderProductEntity)
}