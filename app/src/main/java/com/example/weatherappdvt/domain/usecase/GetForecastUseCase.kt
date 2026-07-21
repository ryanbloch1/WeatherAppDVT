package com.example.weatherappdvt.domain.usecase

import com.example.weatherappdvt.domain.model.Forecast
import com.example.weatherappdvt.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Forecast =
        repository.getForecast(latitude, longitude)
}
