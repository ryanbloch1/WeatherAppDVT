package com.example.weatherappdvt.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponseDto(
    val cod: String,
    val cnt: Int,
    val list: List<ForecastItemDto>,
    val city: CityDto
)

@Serializable
data class ForecastItemDto(
    val dt: Long,
    val main: MainDto,
    val weather: List<WeatherConditionDto>,
    val clouds: CloudsDto,
    val wind: WindDto,
    val visibility: Int? = null,
    val pop: Double? = null,
    @SerialName("dt_txt")
    val dtTxt: String
)

@Serializable
data class CityDto(
    val id: Int,
    val name: String,
    val coord: CoordDto,
    val country: String? = null,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)
