package com.example.weatherappdvt.domain.usecase

import com.example.weatherappdvt.domain.model.DailyForecast
import com.example.weatherappdvt.domain.model.Forecast
import com.example.weatherappdvt.domain.model.WeatherCondition
import com.example.weatherappdvt.domain.repository.WeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

private class FakeWeatherRepository(
    private val forecast: Forecast
) : WeatherRepository {
    override suspend fun getForecast(latitude: Double, longitude: Double): Forecast = forecast
}

class GetForecastUseCaseTest {

    private val sampleForecast = Forecast(
        cityName = "London",
        condition = WeatherCondition.SUNNY,
        days = listOf(
            DailyForecast(
                dayLabel = "Monday",
                temp = 20.0,
                weatherMain = "Clear",
                weatherIcon = "01d",
                description = "clear sky"
            )
        )
    )

    @Test
    fun `invoke returns forecast from repository`() = runTest {
        val useCase = GetForecastUseCase(FakeWeatherRepository(sampleForecast))

        val result = useCase(latitude = 51.5, longitude = -0.1)

        assertEquals(sampleForecast, result)
    }
}
