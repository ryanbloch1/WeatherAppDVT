package com.example.weatherappdvt.domain.repository

import com.example.weatherappdvt.domain.model.Weather

interface WeatherRepository {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): Weather
}
