package com.ewentteamfsm.features.task.api

import com.ewentteamfsm.features.timesheet.api.TimeSheetApi
import com.ewentteamfsm.features.timesheet.api.TimeSheetRepo

/**
 * Created by Saikat on 12-Aug-20.
 */
object TaskRepoProvider {
    fun taskRepoProvider(): TaskRepo {
        return TaskRepo(TaskApi.create())
    }
}