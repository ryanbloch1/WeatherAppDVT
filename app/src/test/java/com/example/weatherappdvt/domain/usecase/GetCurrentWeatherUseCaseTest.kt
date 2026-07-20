package com.example.weatherappdvt.domain.usecase

import com.example.weatherappdvt.domain.model.Weather
import com.example.weatherappdvt.domain.repository.WeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

private class FakeWeatherRepository(
    private val weather: Weather
) : WeatherRepository {
    override suspend fun getCurrentWeather(latitude: Double, longitude: Double): Weather = weather
}

class GetCurrentWeatherUseCaseTest {

    private val sampleWeather = Weather(
        latitude = 51.5,
        longitude = -0.1,
        timezone = "Europe/London",
        timestamp = 1_777_449_371L,
        temp = 13.27,
        feelsLike = 12.17,
        humidity = 58,
        description = "sky is clear",
        weatherIcon = "01d",
        windSpeed = 8.23,
        windDirection = 70,
        pressure = 1024,
        cloudCoverage = 0,
        uvIndex = 1.55,
        visibility = 10000,
        sunrise = 1_777_437_375L,
        sunset = 1_777_490_344L
    )

    @Test
    fun `invoke returns weather from repository`() = runTest {
        val useCase = GetCurrentWeatherUseCase(FakeWeatherRepository(sampleWeather))

        val result = useCase(latitude = 51.5, longitude = -0.1)

        assertEquals(sampleWeather, result)
    }
}
