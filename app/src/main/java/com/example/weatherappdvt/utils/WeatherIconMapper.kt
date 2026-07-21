package com.example.weatherappdvt.utils

import androidx.annotation.DrawableRes
import com.example.weatherappdvt.R

@DrawableRes
fun mapWeatherIcon(iconCode: String): Int =
    when (iconCode) {
        "01d" -> R.drawable.ic_weather_sun
        "01n" -> R.drawable.ic_weather_full_moon
        "02d" -> R.drawable.ic_weather_partial_cloudy
        "02n" -> R.drawable.ic_weather_cloudy_night
        "03d", "03n" -> R.drawable.ic_weather_cloud
        "04d" -> R.drawable.ic_weather_mostly_cloud
        "04n" -> R.drawable.ic_weather_mostly_cloudy
        "09d", "09n" -> R.drawable.ic_weather_heavy_rain
        "10d" -> R.drawable.ic_weather_rainy_day
        "10n" -> R.drawable.ic_weather_rain
        "11d", "11n" -> R.drawable.ic_weather_thunderstorm
        "13d", "13n" -> R.drawable.ic_weather_snow
        "50d", "50n" -> R.drawable.ic_weather_drop
        else -> R.drawable.ic_weather_sun
    }
