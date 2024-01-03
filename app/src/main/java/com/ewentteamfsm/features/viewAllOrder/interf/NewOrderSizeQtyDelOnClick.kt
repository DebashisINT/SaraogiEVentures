package com.ewentteamfsm.features.viewAllOrder.interf

import com.ewentteamfsm.app.domain.NewOrderGenderEntity
import com.ewentteamfsm.features.viewAllOrder.model.ProductOrder
import java.text.FieldPosition

interface NewOrderSizeQtyDelOnClick {
    fun sizeQtySelListOnClick(product_size_qty: ArrayList<ProductOrder>)
    fun sizeQtyListOnClick(product_size_qty: ProductOrder,position: Int)
}