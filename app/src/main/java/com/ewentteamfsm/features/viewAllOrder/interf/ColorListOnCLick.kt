package com.ewentteamfsm.features.viewAllOrder.interf

import com.ewentteamfsm.app.domain.NewOrderGenderEntity
import com.ewentteamfsm.features.viewAllOrder.model.ProductOrder

interface ColorListOnCLick {
    fun colorListOnCLick(size_qty_list: ArrayList<ProductOrder>, adpPosition:Int)
}