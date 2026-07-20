package com.example.weatherappdvt.data.mapper

import com.example.weatherappdvt.data.remote.dto.WeatherDataDto
import com.example.weatherappdvt.domain.model.Weather

fun WeatherDataDto.toDomain(latitude: Double, longitude: Double, timezone: String): Weather =
    Weather(
        latitude = latitude,
        longitude = longitude,
        timezone = timezone,
        timestamp = dt,
        temp = temp,
        feelsLike = feelsLike,
        humidity = humidity,
        description = weather.firstOrNull()?.description ?: "Unknown",
        weatherIcon = weather.firstOrNull()?.icon ?: "01d",
        windSpeed = windSpeed,
        windDirection = windDeg,
        pressure = pressure,
        cloudCoverage = clouds,
        uvIndex = uvi,
        visibility = visibility,
        sunrise = sunrise,
        sunset = sunset,
        alerts = alerts ?: emptyList()
    )
