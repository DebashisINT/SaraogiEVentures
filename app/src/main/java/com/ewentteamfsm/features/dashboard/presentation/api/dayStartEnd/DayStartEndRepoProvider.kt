package com.ewentteamfsm.features.dashboard.presentation.api.dayStartEnd

import com.ewentteamfsm.features.stockCompetetorStock.api.AddCompStockApi
import com.ewentteamfsm.features.stockCompetetorStock.api.AddCompStockRepository

object DayStartEndRepoProvider {
    fun dayStartRepositiry(): DayStartEndRepository {
        return DayStartEndRepository(DayStartEndApi.create())
    }

}