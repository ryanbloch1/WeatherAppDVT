package com.example.weatherappdvt.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordDto(
    val lon: Double,
    val lat: Double
)

@Serializable
data class MainDto(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

@Serializable
data class WindDto(
    val speed: Double,
    val deg: Int,
    val gust: Double? = null
)

@Serializable
data class CloudsDto(
    val all: Int
)

@Serializable
data class WeatherConditionDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
