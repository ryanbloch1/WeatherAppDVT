package com.example.weatherappdvt.data.repository

import com.example.weatherappdvt.data.remote.WeatherApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class WeatherRepositoryImplTest {

    private lateinit var server: MockWebServer
    private lateinit var apiService: WeatherApiService
    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()

        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        apiService = retrofit.create(WeatherApiService::class.java)
        repository = WeatherRepositoryImpl(apiService, "test-api-key")
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getCurrentWeather maps API response to domain model`() = runTest {
        val responseBody = """
            {
              "lat": 51.5,
              "lon": -0.1,
              "timezone": "Europe/London",
              "timezone_offset": 3600,
              "data": [
                {
                  "dt": 1777449371,
                  "sunrise": 1777437375,
                  "sunset": 1777490344,
                  "temp": 13.27,
                  "feels_like": 12.17,
                  "pressure": 1024,
                  "humidity": 58,
                  "dew_point": 5.19,
                  "uvi": 1.55,
                  "clouds": 0,
                  "visibility": 10000,
                  "wind_speed": 8.23,
                  "wind_deg": 70,
                  "weather": [
                    {
                      "id": 800,
                      "main": "Clear",
                      "description": "sky is clear",
                      "icon": "01d"
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

        server.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        val weather = repository.getCurrentWeather(latitude = 51.5, longitude = -0.1)

        assertEquals("Europe/London", weather.timezone)
        assertEquals(13.27, weather.temp, 0.0)
        assertEquals("sky is clear", weather.description)
        assertEquals("01d", weather.weatherIcon)
        assertEquals(58, weather.humidity)
    }

    @Test
    fun `getCurrentWeather throws when data list is empty`() = runTest {
        val responseBody = """
            {
              "lat": 51.5,
              "lon": -0.1,
              "timezone": "Europe/London",
              "timezone_offset": 3600,
              "data": []
            }
        """.trimIndent()

        server.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        try {
            repository.getCurrentWeather(latitude = 51.5, longitude = -0.1)
            org.junit.Assert.fail("Expected IllegalStateException")
        } catch (e: IllegalStateException) {
            assertEquals("No weather data in response", e.message)
        }
    }
}
