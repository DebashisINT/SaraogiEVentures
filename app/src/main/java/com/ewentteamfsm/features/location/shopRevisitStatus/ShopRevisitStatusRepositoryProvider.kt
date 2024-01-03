package com.ewentteamfsm.features.location.shopRevisitStatus

import com.ewentteamfsm.features.location.shopdurationapi.ShopDurationApi
import com.ewentteamfsm.features.location.shopdurationapi.ShopDurationRepository

object ShopRevisitStatusRepositoryProvider {
    fun provideShopRevisitStatusRepository(): ShopRevisitStatusRepository {
        return ShopRevisitStatusRepository(ShopRevisitStatusApi.create())
    }
}