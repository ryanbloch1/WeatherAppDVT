package com.example.weatherappdvt.domain.model

data class Forecast(
    val cityName: String,
    val condition: WeatherCondition,
    val days: List<DailyForecast>
)
