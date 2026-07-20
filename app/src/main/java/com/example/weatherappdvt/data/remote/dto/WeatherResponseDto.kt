package com.example.weatherappdvt.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val coord: CoordDto,
    val weather: List<WeatherConditionDto>,
    val main: MainDto,
    val visibility: Int,
    val wind: WindDto,
    val clouds: CloudsDto,
    val dt: Long,
    val sys: SysDto,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

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
data class SysDto(
    val type: Int? = null,
    val id: Int? = null,
    val country: String? = null,
    val sunrise: Long,
    val sunset: Long
)

@Serializable
data class WeatherConditionDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
