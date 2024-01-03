package com.ewentteamfsm.features.stockAddCurrentStock.api

import com.ewentteamfsm.base.BaseResponse
import com.ewentteamfsm.features.location.model.ShopRevisitStatusRequest
import com.ewentteamfsm.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.ewentteamfsm.features.stockAddCurrentStock.ShopAddCurrentStockRequest
import com.ewentteamfsm.features.stockAddCurrentStock.model.CurrentStockGetData
import com.ewentteamfsm.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class ShopAddStockRepository (val apiService : ShopAddStockApi){
    fun shopAddStock(shopAddCurrentStockRequest: ShopAddCurrentStockRequest?): Observable<BaseResponse> {
        return apiService.submShopAddStock(shopAddCurrentStockRequest)
    }

    fun getCurrStockList(sessiontoken: String, user_id: String, date: String): Observable<CurrentStockGetData> {
        return apiService.getCurrStockListApi(sessiontoken, user_id, date)
    }

}