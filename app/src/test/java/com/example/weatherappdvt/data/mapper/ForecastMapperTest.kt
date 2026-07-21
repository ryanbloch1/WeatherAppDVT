package com.example.weatherappdvt.data.mapper

import com.example.weatherappdvt.data.remote.dto.CityDto
import com.example.weatherappdvt.data.remote.dto.CloudsDto
import com.example.weatherappdvt.data.remote.dto.CoordDto
import com.example.weatherappdvt.data.remote.dto.ForecastItemDto
import com.example.weatherappdvt.data.remote.dto.ForecastResponseDto
import com.example.weatherappdvt.data.remote.dto.MainDto
import com.example.weatherappdvt.data.remote.dto.WeatherConditionDto
import com.example.weatherappdvt.data.remote.dto.WindDto
import com.example.weatherappdvt.domain.model.WeatherCondition
import org.junit.Assert.assertEquals
import org.junit.Test

private const val SECONDS_PER_DAY = 86_400L
private const val SECONDS_PER_HOUR = 3_600L

// Epoch day 0 (1 Jan 1970) is a known Thursday.
private fun itemAt(epochDay: Long, hour: Int, temp: Double, main: String = "Clear", icon: String = "01d"): ForecastItemDto {
    val dt = epochDay * SECONDS_PER_DAY + hour * SECONDS_PER_HOUR
    return ForecastItemDto(
        dt = dt,
        main = MainDto(temp = temp, feelsLike = temp, tempMin = temp, tempMax = temp, pressure = 1013, humidity = 50),
        weather = listOf(WeatherConditionDto(id = 800, main = main, description = "$main sky", icon = icon)),
        clouds = CloudsDto(all = 0),
        wind = WindDto(speed = 1.0, deg = 0),
        visibility = 10000,
        dtTxt = "unused"
    )
}

private fun buildResponse(items: List<ForecastItemDto>): ForecastResponseDto = ForecastResponseDto(
    cod = "200",
    cnt = items.size,
    list = items,
    city = CityDto(
        id = 1,
        name = "London",
        coord = CoordDto(lat = 51.5, lon = -0.1),
        timezone = 0,
        sunrise = 0,
        sunset = 0
    )
)

class ForecastMapperTest {

    @Test
    fun `buckets 3-hour entries into 5 days picking the entry nearest noon`() {
        val items = (0..5L).flatMap { epochDay ->
            listOf(
                itemAt(epochDay, hour = 9, temp = 20.0 + epochDay),
                itemAt(epochDay, hour = 12, temp = 30.0 + epochDay)
            )
        }

        val forecast = buildResponse(items).toDomain()

        assertEquals(5, forecast.days.size)
        assertEquals(listOf("Thursday", "Friday", "Saturday", "Sunday", "Monday"), forecast.days.map { it.dayLabel })
        assertEquals(listOf(30.0, 31.0, 32.0, 33.0, 34.0), forecast.days.map { it.temp })
    }

    @Test
    fun `condition is derived from the first entry's weather group`() {
        val items = listOf(
            itemAt(epochDay = 0, hour = 9, temp = 20.0, main = "Rain", icon = "10d"),
            itemAt(epochDay = 0, hour = 12, temp = 21.0, main = "Clear", icon = "01d")
        )

        val forecast = buildResponse(items).toDomain()

        assertEquals(WeatherCondition.RAINY, forecast.condition)
    }

    @Test
    fun `city name is carried through to the domain model`() {
        val forecast = buildResponse(listOf(itemAt(0, 12, 20.0))).toDomain()

        assertEquals("London", forecast.cityName)
    }
}
