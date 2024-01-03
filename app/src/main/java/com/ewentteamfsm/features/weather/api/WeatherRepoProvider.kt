package com.ewentteamfsm.features.weather.api

import com.ewentteamfsm.features.task.api.TaskApi
import com.ewentteamfsm.features.task.api.TaskRepo

object WeatherRepoProvider {
    fun weatherRepoProvider(): WeatherRepo {
        return WeatherRepo(WeatherApi.create())
    }
}