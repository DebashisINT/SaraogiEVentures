package com.ewentteamfsm.features.nearbyuserlist.api

import com.ewentteamfsm.app.Pref
import com.ewentteamfsm.features.nearbyuserlist.model.NearbyUserResponseModel
import com.ewentteamfsm.features.newcollection.model.NewCollectionListResponseModel
import com.ewentteamfsm.features.newcollection.newcollectionlistapi.NewCollectionListApi
import io.reactivex.Observable

class NearbyUserRepo(val apiService: NearbyUserApi) {
    fun nearbyUserList(): Observable<NearbyUserResponseModel> {
        return apiService.getNearbyUserList(Pref.session_token!!, Pref.user_id!!)
    }
}