package com.example.weatherappdvt.presentation.forecast

import app.cash.turbine.test
import com.example.weatherappdvt.domain.model.DailyForecast
import com.example.weatherappdvt.domain.model.Forecast
import com.example.weatherappdvt.domain.model.LocationCoordinates
import com.example.weatherappdvt.domain.model.WeatherCondition
import com.example.weatherappdvt.domain.usecase.GetCurrentLocationUseCase
import com.example.weatherappdvt.domain.usecase.GetForecastUseCase
import com.example.weatherappdvt.domain.repository.LocationRepository
import com.example.weatherappdvt.domain.repository.WeatherRepository
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
class ForecastViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val locationRepository: LocationRepository = mockk()
    private val weatherRepository: WeatherRepository = mockk()

    private val sampleLocation = LocationCoordinates(latitude = 51.5, longitude = -0.1)
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

    private fun buildViewModel() = ForecastViewModel(
        GetCurrentLocationUseCase(locationRepository),
        GetForecastUseCase(weatherRepository)
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
    fun `starts in PermissionRequired`() = runTest {
        val viewModel = buildViewModel()

        viewModel.uiState.test {
            assertEquals(ForecastUiState.PermissionRequired, awaitItem())
        }
    }

    @Test
    fun `onLocationPermissionGranted emits Loading then Success`() = runTest {
        coEvery { locationRepository.getCurrentLocation() } returns sampleLocation
        coEvery { weatherRepository.getForecast(51.5, -0.1) } returns sampleForecast
        val viewModel = buildViewModel()

        viewModel.uiState.test {
            assertEquals(ForecastUiState.PermissionRequired, awaitItem())

            viewModel.onLocationPermissionGranted()
            assertEquals(ForecastUiState.Loading, awaitItem())

            testDispatcher.scheduler.advanceUntilIdle()

            val success = awaitItem()
            assertTrue(success is ForecastUiState.Success)
            assertEquals(sampleForecast, (success as ForecastUiState.Success).forecast)
        }
    }

    @Test
    fun `onLocationPermissionGranted emits Error when location fetch throws`() = runTest {
        coEvery { locationRepository.getCurrentLocation() } throws RuntimeException("location unavailable")
        val viewModel = buildViewModel()

        viewModel.uiState.test {
            assertEquals(ForecastUiState.PermissionRequired, awaitItem())

            viewModel.onLocationPermissionGranted()
            assertEquals(ForecastUiState.Loading, awaitItem())

            testDispatcher.scheduler.advanceUntilIdle()

            val error = awaitItem()
            assertTrue(error is ForecastUiState.Error)
            assertEquals("location unavailable", (error as ForecastUiState.Error).message)
        }
    }

    @Test
    fun `onLocationPermissionDenied emits Error`() = runTest {
        val viewModel = buildViewModel()

        viewModel.uiState.test {
            assertEquals(ForecastUiState.PermissionRequired, awaitItem())

            viewModel.onLocationPermissionDenied()

            val error = awaitItem()
            assertTrue(error is ForecastUiState.Error)
        }
    }
}
