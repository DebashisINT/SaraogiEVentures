package com.ewentteamfsm.features.location.shopRevisitStatus

import com.ewentteamfsm.base.BaseResponse
import com.ewentteamfsm.features.location.model.ShopDurationRequest
import com.ewentteamfsm.features.location.model.ShopRevisitStatusRequest
import io.reactivex.Observable

class ShopRevisitStatusRepository(val apiService : ShopRevisitStatusApi) {
    fun shopRevisitStatus(shopRevisitStatus: ShopRevisitStatusRequest?): Observable<BaseResponse> {
        return apiService.submShopRevisitStatus(shopRevisitStatus)
    }
}