package com.ewentteamfsm.features.report.api.report_api

import com.ewentteamfsm.app.Pref
import com.ewentteamfsm.features.report.model.AchievementResponseModel
import com.ewentteamfsm.features.report.model.TargetVsAchvResponseModel
import io.reactivex.Observable

/**
 * Created by Saikat on 22-Jul-20.
 */
class ReportRepo(val apiService: ReportApi) {
    fun getAchvReport(from_date: String, to_date: String): Observable<AchievementResponseModel> {
        return apiService.getAchievementList(Pref.session_token!!, Pref.user_id!!, from_date, to_date)
    }

    fun getTargVsAchvReport(from_date: String, to_date: String): Observable<TargetVsAchvResponseModel> {
        return apiService.getTargVsAchvList(Pref.session_token!!, Pref.user_id!!, from_date, to_date)
    }
}