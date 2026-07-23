package com.example.weatherappdvt.data.repository

import com.example.weatherappdvt.data.remote.WeatherApiService
import com.example.weatherappdvt.domain.model.WeatherCondition
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

    @Suppress("LongMethod") // long due to the embedded realistic JSON fixture, not actual test logic
    @Test
    fun `getForecast maps API response into 5 daily forecasts`() = runTest {
        // 1970-01-01 (epoch day 0) is a Thursday; five 3-hour entries land on distinct days.
        val responseBody = """
            {
              "cod": "200",
              "message": 0,
              "cnt": 5,
              "list": [
                {
                  "dt": 43200,
                  "main": {"temp": 20.0, "feels_like": 19.5, "temp_min": 19.0, "temp_max": 21.0, "pressure": 1013, "humidity": 55},
                  "weather": [{"id": 800, "main": "Clear", "description": "clear sky", "icon": "01d"}],
                  "clouds": {"all": 0},
                  "wind": {"speed": 3.1, "deg": 180},
                  "visibility": 10000,
                  "pop": 0.0,
                  "dt_txt": "1970-01-01 12:00:00"
                },
                {
                  "dt": 129600,
                  "main": {"temp": 21.0, "feels_like": 20.5, "temp_min": 20.0, "temp_max": 22.0, "pressure": 1013, "humidity": 55},
                  "weather": [{"id": 800, "main": "Clear", "description": "clear sky", "icon": "01d"}],
                  "clouds": {"all": 0},
                  "wind": {"speed": 3.1, "deg": 180},
                  "visibility": 10000,
                  "pop": 0.0,
                  "dt_txt": "1970-01-02 12:00:00"
                },
                {
                  "dt": 216000,
                  "main": {"temp": 22.0, "feels_like": 21.5, "temp_min": 21.0, "temp_max": 23.0, "pressure": 1013, "humidity": 55},
                  "weather": [{"id": 800, "main": "Clear", "description": "clear sky", "icon": "01d"}],
                  "clouds": {"all": 0},
                  "wind": {"speed": 3.1, "deg": 180},
                  "visibility": 10000,
                  "pop": 0.0,
                  "dt_txt": "1970-01-03 12:00:00"
                },
                {
                  "dt": 302400,
                  "main": {"temp": 23.0, "feels_like": 22.5, "temp_min": 22.0, "temp_max": 24.0, "pressure": 1013, "humidity": 55},
                  "weather": [{"id": 800, "main": "Clear", "description": "clear sky", "icon": "01d"}],
                  "clouds": {"all": 0},
                  "wind": {"speed": 3.1, "deg": 180},
                  "visibility": 10000,
                  "pop": 0.0,
                  "dt_txt": "1970-01-04 12:00:00"
                },
                {
                  "dt": 388800,
                  "main": {"temp": 24.0, "feels_like": 23.5, "temp_min": 23.0, "temp_max": 25.0, "pressure": 1013, "humidity": 55},
                  "weather": [{"id": 800, "main": "Clear", "description": "clear sky", "icon": "01d"}],
                  "clouds": {"all": 0},
                  "wind": {"speed": 3.1, "deg": 180},
                  "visibility": 10000,
                  "pop": 0.0,
                  "dt_txt": "1970-01-05 12:00:00"
                }
              ],
              "city": {
                "id": 2643743,
                "name": "London",
                "coord": {"lat": 51.5, "lon": -0.1},
                "country": "GB",
                "timezone": 0,
                "sunrise": 30000,
                "sunset": 60000
              }
            }
        """.trimIndent()

        server.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        val forecast = repository.getForecast(latitude = 51.5, longitude = -0.1)

        assertEquals("London", forecast.cityName)
        assertEquals(WeatherCondition.SUNNY, forecast.condition)
        assertEquals(5, forecast.days.size)
        assertEquals(listOf("Thursday", "Friday", "Saturday", "Sunday", "Monday"), forecast.days.map { it.dayLabel })
        assertEquals(listOf(20.0, 21.0, 22.0, 23.0, 24.0), forecast.days.map { it.temp })
    }
}
