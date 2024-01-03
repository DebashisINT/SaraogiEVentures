package com.ewentteamfsm.features.stockAddCurrentStock.api

import com.ewentteamfsm.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.ewentteamfsm.features.location.shopRevisitStatus.ShopRevisitStatusRepository

object ShopAddStockProvider {
    fun provideShopAddStockRepository(): ShopAddStockRepository {
        return ShopAddStockRepository(ShopAddStockApi.create())
    }
}