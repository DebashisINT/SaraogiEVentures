package com.ewentteamfsm.features.photoReg.present

import com.ewentteamfsm.app.domain.ProspectEntity
import com.ewentteamfsm.features.photoReg.model.UserListResponseModel

interface DsStatusListner {
    fun getDSInfoOnLick(obj: ProspectEntity)
}