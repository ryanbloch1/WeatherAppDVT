package com.example.weatherappdvt.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDataDto(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    @SerialName("wind_speed")
    val windSpeed: Double,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_gust")
    val windGust: Double? = null,
    val weather: List<WeatherConditionDto>,
    val alerts: List<String>? = null
)
