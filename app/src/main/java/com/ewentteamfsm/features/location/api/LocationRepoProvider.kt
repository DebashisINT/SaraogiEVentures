package com.ewentteamfsm.features.location.api

import com.ewentteamfsm.features.location.shopdurationapi.ShopDurationApi
import com.ewentteamfsm.features.location.shopdurationapi.ShopDurationRepository


object LocationRepoProvider {
    fun provideLocationRepository(): LocationRepo {
        return LocationRepo(LocationApi.create())
    }
}