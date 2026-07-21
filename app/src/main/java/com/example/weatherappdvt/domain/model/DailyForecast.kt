package com.example.weatherappdvt.domain.model

data class DailyForecast(
    val dayLabel: String,
    val temp: Double,
    val weatherMain: String,
    val weatherIcon: String,
    val description: String
)
