package com.ewentteamfsm.features.lead.api

import com.ewentteamfsm.features.NewQuotation.api.GetQuotListRegRepository
import com.ewentteamfsm.features.NewQuotation.api.GetQutoListApi


object GetLeadRegProvider {
    fun provideList(): GetLeadListRegRepository {
        return GetLeadListRegRepository(GetLeadListApi.create())
    }
}