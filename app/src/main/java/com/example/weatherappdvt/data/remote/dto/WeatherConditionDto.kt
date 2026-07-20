package com.example.weatherappdvt.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherConditionDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
