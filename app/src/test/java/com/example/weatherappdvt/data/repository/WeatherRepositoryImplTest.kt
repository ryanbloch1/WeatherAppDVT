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
              "coord": {"lon": -0.1, "lat": 51.5},
              "weather": [
                {
                  "id": 800,
                  "main": "Clear",
                  "description": "sky is clear",
                  "icon": "01d"
                }
              ],
              "main": {
                "temp": 13.27,
                "feels_like": 12.17,
                "temp_min": 12.5,
                "temp_max": 14.0,
                "pressure": 1024,
                "humidity": 58
              },
              "visibility": 10000,
              "wind": {"speed": 8.23, "deg": 70},
              "clouds": {"all": 0},
              "dt": 1777449371,
              "sys": {
                "country": "GB",
                "sunrise": 1777437375,
                "sunset": 1777490344
              },
              "timezone": 0,
              "id": 2643743,
              "name": "London",
              "cod": 200
            }
        """.trimIndent()

        server.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        val weather = repository.getCurrentWeather(latitude = 51.5, longitude = -0.1)

        assertEquals("London", weather.timezone)
        assertEquals(13.27, weather.temp, 0.0)
        assertEquals("sky is clear", weather.description)
        assertEquals("01d", weather.weatherIcon)
        assertEquals(58, weather.humidity)
    }
}
