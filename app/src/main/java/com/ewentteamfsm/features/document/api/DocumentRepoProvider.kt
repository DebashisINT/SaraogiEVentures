package com.ewentteamfsm.features.document.api

import com.ewentteamfsm.features.dymanicSection.api.DynamicApi
import com.ewentteamfsm.features.dymanicSection.api.DynamicRepo

object DocumentRepoProvider {
    fun documentRepoProvider(): DocumentRepo {
        return DocumentRepo(DocumentApi.create())
    }

    fun documentRepoProviderMultipart(): DocumentRepo {
        return DocumentRepo(DocumentApi.createImage())
    }
}