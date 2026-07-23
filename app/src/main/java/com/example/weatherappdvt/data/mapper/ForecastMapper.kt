package com.example.weatherappdvt.data.mapper

import com.example.weatherappdvt.data.remote.dto.ForecastItemDto
import com.example.weatherappdvt.data.remote.dto.ForecastResponseDto
import com.example.weatherappdvt.domain.model.DailyForecast
import com.example.weatherappdvt.domain.model.Forecast
import com.example.weatherappdvt.utils.mapWeatherConditionGroup

private val DAY_NAMES_FROM_EPOCH_DAY_ZERO =
    listOf("Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday")

private const val SECONDS_PER_DAY = 86_400L
private const val SECONDS_PER_HOUR = 3_600L
private const val NOON_HOUR = 12
private const val DAYS_IN_WEEK = 7L
private const val FORECAST_DAY_COUNT = 5

private fun ForecastItemDto.localEpochSeconds(timezoneOffsetSeconds: Int): Long =
    dt + timezoneOffsetSeconds

private fun localEpochDay(localEpochSeconds: Long): Long =
    Math.floorDiv(localEpochSeconds, SECONDS_PER_DAY)

private fun localHourOfDay(localEpochSeconds: Long): Int {
    val secondOfDay = Math.floorMod(localEpochSeconds, SECONDS_PER_DAY)
    return (secondOfDay / SECONDS_PER_HOUR).toInt()
}

private fun dayLabelFor(epochDay: Long): String {
    val index = Math.floorMod(epochDay, DAYS_IN_WEEK).toInt()
    return DAY_NAMES_FROM_EPOCH_DAY_ZERO[index]
}

fun ForecastResponseDto.toDomain(): Forecast {
    val timezoneOffset = city.timezone

    val itemsByDay = list.groupBy { item ->
        localEpochDay(item.localEpochSeconds(timezoneOffset))
    }

    val dailyForecasts = itemsByDay.entries
        .sortedBy { it.key }
        .take(FORECAST_DAY_COUNT)
        .map { (epochDay, items) ->
            val representative = items.minBy { item ->
                kotlin.math.abs(localHourOfDay(item.localEpochSeconds(timezoneOffset)) - NOON_HOUR)
            }
            val condition = representative.weather.firstOrNull()
            DailyForecast(
                dayLabel = dayLabelFor(epochDay),
                temp = representative.main.temp,
                weatherMain = condition?.main ?: "Clear",
                weatherIcon = condition?.icon ?: "01d",
                description = condition?.description ?: "Unknown"
            )
        }

    val firstConditionMain = list.firstOrNull()?.weather?.firstOrNull()?.main ?: "Clear"

    return Forecast(
        cityName = city.name,
        condition = mapWeatherConditionGroup(firstConditionMain),
        days = dailyForecasts
    )
}
