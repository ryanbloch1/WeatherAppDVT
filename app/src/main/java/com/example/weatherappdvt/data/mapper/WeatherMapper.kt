package com.example.weatherappdvt.data.mapper

import com.example.weatherappdvt.data.remote.dto.WeatherResponseDto
import com.example.weatherappdvt.domain.model.Weather

fun WeatherResponseDto.toDomain(): Weather =
    Weather(
        latitude = coord.lat,
        longitude = coord.lon,
        timezone = name,
        timestamp = dt,
        temp = main.temp,
        feelsLike = main.feelsLike,
        humidity = main.humidity,
        description = weather.firstOrNull()?.description ?: "Unknown",
        weatherIcon = weather.firstOrNull()?.icon ?: "01d",
        windSpeed = wind.speed,
        windDirection = wind.deg,
        pressure = main.pressure,
        cloudCoverage = clouds.all,
        uvIndex = 0.0,
        visibility = visibility,
        sunrise = sys.sunrise,
        sunset = sys.sunset,
        alerts = emptyList()
    )
