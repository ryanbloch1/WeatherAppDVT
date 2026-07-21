package com.example.weatherappdvt.utils

import com.example.weatherappdvt.R
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherIconMapperTest {

    @Test
    fun `clear day and night map to distinct icons`() {
        assertEquals(R.drawable.ic_weather_sun, mapWeatherIcon("01d"))
        assertEquals(R.drawable.ic_weather_full_moon, mapWeatherIcon("01n"))
    }

    @Test
    fun `rain codes map to rain icons`() {
        assertEquals(R.drawable.ic_weather_rainy_day, mapWeatherIcon("10d"))
        assertEquals(R.drawable.ic_weather_rain, mapWeatherIcon("10n"))
    }

    @Test
    fun `thunderstorm codes map to thunderstorm icon regardless of day or night`() {
        assertEquals(R.drawable.ic_weather_thunderstorm, mapWeatherIcon("11d"))
        assertEquals(R.drawable.ic_weather_thunderstorm, mapWeatherIcon("11n"))
    }

    @Test
    fun `unknown icon code falls back to sun icon`() {
        assertEquals(R.drawable.ic_weather_sun, mapWeatherIcon("99x"))
    }
}
