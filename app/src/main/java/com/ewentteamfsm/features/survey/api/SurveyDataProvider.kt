package com.ewentteamfsm.features.survey.api

import com.ewentteamfsm.features.photoReg.api.GetUserListPhotoRegApi
import com.ewentteamfsm.features.photoReg.api.GetUserListPhotoRegRepository

object SurveyDataProvider{

    fun provideSurveyQ(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.create())
    }

    fun provideSurveyQMultiP(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.createImage())
    }
}