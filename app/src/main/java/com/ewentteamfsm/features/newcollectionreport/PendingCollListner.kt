package com.ewentteamfsm.features.newcollectionreport

import com.ewentteamfsm.features.photoReg.model.UserListResponseModel

interface PendingCollListner {
    fun getUserInfoOnLick(obj: PendingCollData)
}