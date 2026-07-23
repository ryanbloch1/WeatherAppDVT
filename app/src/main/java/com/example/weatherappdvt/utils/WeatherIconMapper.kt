package com.example.weatherappdvt.utils

import androidx.annotation.DrawableRes
import com.example.weatherappdvt.R

private val ICON_CODE_TO_DRAWABLE: Map<String, Int> = mapOf(
    "01d" to R.drawable.ic_weather_sun,
    "01n" to R.drawable.ic_weather_full_moon,
    "02d" to R.drawable.ic_weather_partial_cloudy,
    "02n" to R.drawable.ic_weather_cloudy_night,
    "03d" to R.drawable.ic_weather_cloud,
    "03n" to R.drawable.ic_weather_cloud,
    "04d" to R.drawable.ic_weather_mostly_cloud,
    "04n" to R.drawable.ic_weather_mostly_cloudy,
    "09d" to R.drawable.ic_weather_heavy_rain,
    "09n" to R.drawable.ic_weather_heavy_rain,
    "10d" to R.drawable.ic_weather_rainy_day,
    "10n" to R.drawable.ic_weather_rain,
    "11d" to R.drawable.ic_weather_thunderstorm,
    "11n" to R.drawable.ic_weather_thunderstorm,
    "13d" to R.drawable.ic_weather_snow,
    "13n" to R.drawable.ic_weather_snow,
    "50d" to R.drawable.ic_weather_drop,
    "50n" to R.drawable.ic_weather_drop
)

@DrawableRes
fun mapWeatherIcon(iconCode: String): Int =
    ICON_CODE_TO_DRAWABLE[iconCode] ?: R.drawable.ic_weather_sun
