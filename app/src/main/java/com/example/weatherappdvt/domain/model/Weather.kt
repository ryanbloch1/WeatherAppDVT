package com.example.weatherappdvt.domain.model

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timestamp: Long,
    val temp: Double,
    val feelsLike: Double,
    val humidity: Int,
    val description: String,
    val weatherIcon: String,
    val windSpeed: Double,
    val windDirection: Int,
    val pressure: Int,
    val cloudCoverage: Int,
    val uvIndex: Double,
    val visibility: Int,
    val sunrise: Long,
    val sunset: Long,
    val alerts: List<String> = emptyList()
)
