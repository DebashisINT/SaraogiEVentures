package com.ewentteamfsm.features.weather.api

import com.ewentteamfsm.base.BaseResponse
import com.ewentteamfsm.features.task.api.TaskApi
import com.ewentteamfsm.features.task.model.AddTaskInputModel
import com.ewentteamfsm.features.weather.model.ForeCastAPIResponse
import com.ewentteamfsm.features.weather.model.WeatherAPIResponse
import io.reactivex.Observable

class WeatherRepo(val apiService: WeatherApi) {
    fun getCurrentWeather(zipCode: String): Observable<WeatherAPIResponse> {
        return apiService.getTodayWeather(zipCode)
    }

    fun getWeatherForecast(zipCode: String): Observable<ForeCastAPIResponse> {
        return apiService.getForecast(zipCode)
    }
}