package com.example.weatherappdvt.domain.repository

import com.example.weatherappdvt.domain.model.Forecast

interface WeatherRepository {
    suspend fun getForecast(latitude: Double, longitude: Double): Forecast
}
