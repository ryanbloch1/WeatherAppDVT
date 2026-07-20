package com.example.weatherappdvt.presentation.weather

import app.cash.turbine.test
import com.example.weatherappdvt.domain.model.Weather
import com.example.weatherappdvt.domain.repository.WeatherRepository
import com.example.weatherappdvt.domain.usecase.GetCurrentWeatherUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository: WeatherRepository = mockk()

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

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadWeather emits Loading then Success`() = runTest {
        coEvery { repository.getCurrentWeather(51.5, -0.1) } returns sampleWeather
        val viewModel = WeatherViewModel(GetCurrentWeatherUseCase(repository))

        viewModel.uiState.test {
            assertEquals(WeatherUiState.Loading, awaitItem())

            viewModel.loadWeather(51.5, -0.1)
            testDispatcher.scheduler.advanceUntilIdle()

            val success = awaitItem()
            assertTrue(success is WeatherUiState.Success)
            assertEquals(sampleWeather, (success as WeatherUiState.Success).weather)
        }
    }

    @Test
    fun `loadWeather emits Error when repository throws`() = runTest {
        coEvery { repository.getCurrentWeather(any(), any()) } throws RuntimeException("network down")
        val viewModel = WeatherViewModel(GetCurrentWeatherUseCase(repository))

        viewModel.uiState.test {
            assertEquals(WeatherUiState.Loading, awaitItem())

            viewModel.loadWeather(51.5, -0.1)
            testDispatcher.scheduler.advanceUntilIdle()

            val error = awaitItem()
            assertTrue(error is WeatherUiState.Error)
            assertEquals("network down", (error as WeatherUiState.Error).message)
        }
    }
}
