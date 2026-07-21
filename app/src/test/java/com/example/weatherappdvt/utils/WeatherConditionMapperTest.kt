package com.example.weatherappdvt.utils

import com.example.weatherappdvt.domain.model.WeatherCondition
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherConditionMapperTest {

    @Test
    fun `Clear maps to SUNNY`() {
        assertEquals(WeatherCondition.SUNNY, mapWeatherConditionGroup("Clear"))
    }

    @Test
    fun `Clouds maps to CLOUDY`() {
        assertEquals(WeatherCondition.CLOUDY, mapWeatherConditionGroup("Clouds"))
    }

    @Test
    fun `Rain Drizzle and Thunderstorm map to RAINY`() {
        assertEquals(WeatherCondition.RAINY, mapWeatherConditionGroup("Rain"))
        assertEquals(WeatherCondition.RAINY, mapWeatherConditionGroup("Drizzle"))
        assertEquals(WeatherCondition.RAINY, mapWeatherConditionGroup("Thunderstorm"))
    }

    @Test
    fun `unrecognised groups fall back to CLOUDY`() {
        assertEquals(WeatherCondition.CLOUDY, mapWeatherConditionGroup("Snow"))
        assertEquals(WeatherCondition.CLOUDY, mapWeatherConditionGroup("Mist"))
        assertEquals(WeatherCondition.CLOUDY, mapWeatherConditionGroup("SomethingUnknown"))
    }
}
