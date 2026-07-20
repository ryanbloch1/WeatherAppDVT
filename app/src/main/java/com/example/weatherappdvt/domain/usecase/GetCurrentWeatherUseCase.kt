package com.example.weatherappdvt.domain.usecase

import com.example.weatherappdvt.domain.model.Weather
import com.example.weatherappdvt.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Weather =
        repository.getCurrentWeather(latitude, longitude)
}
