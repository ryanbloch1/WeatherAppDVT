package com.example.weatherappdvt.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val data: List<WeatherDataDto>
)
