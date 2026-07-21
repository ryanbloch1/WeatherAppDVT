package com.example.weatherappdvt.data.repository

import com.example.weatherappdvt.data.mapper.toDomain
import com.example.weatherappdvt.data.remote.WeatherApiService
import com.example.weatherappdvt.domain.model.Forecast
import com.example.weatherappdvt.domain.repository.WeatherRepository
import javax.inject.Inject
import javax.inject.Named

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService,
    @Named("weatherApiKey") private val apiKey: String
) : WeatherRepository {
    override suspend fun getForecast(latitude: Double, longitude: Double): Forecast {
        val response = apiService.getForecast(
            latitude = latitude,
            longitude = longitude,
            apiKey = apiKey
        )
        return response.toDomain()
    }
}
