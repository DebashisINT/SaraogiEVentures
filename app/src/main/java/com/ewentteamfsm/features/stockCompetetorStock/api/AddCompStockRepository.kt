package com.ewentteamfsm.features.stockCompetetorStock.api

import com.ewentteamfsm.base.BaseResponse
import com.ewentteamfsm.features.orderList.model.NewOrderListResponseModel
import com.ewentteamfsm.features.stockCompetetorStock.ShopAddCompetetorStockRequest
import com.ewentteamfsm.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class AddCompStockRepository(val apiService:AddCompStockApi){

    fun addCompStock(shopAddCompetetorStockRequest: ShopAddCompetetorStockRequest): Observable<BaseResponse> {
        return apiService.submShopCompStock(shopAddCompetetorStockRequest)
    }

    fun getCompStockList(sessiontoken: String, user_id: String, date: String): Observable<CompetetorStockGetData> {
        return apiService.getCompStockList(sessiontoken, user_id, date)
    }
}