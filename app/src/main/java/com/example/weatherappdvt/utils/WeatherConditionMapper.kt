package com.example.weatherappdvt.utils

import com.example.weatherappdvt.domain.model.WeatherCondition

fun mapWeatherConditionGroup(weatherMain: String): WeatherCondition =
    when (weatherMain) {
        "Clear" -> WeatherCondition.SUNNY
        "Rain", "Drizzle", "Thunderstorm" -> WeatherCondition.RAINY
        else -> WeatherCondition.CLOUDY
    }
