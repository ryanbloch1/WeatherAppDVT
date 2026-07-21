package com.example.weatherappdvt.presentation.forecast

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappdvt.domain.model.DailyForecast
import com.example.weatherappdvt.ui.theme.CardTitleStyle
import com.example.weatherappdvt.ui.theme.TemperatureStyle
import com.example.weatherappdvt.utils.mapWeatherIcon
import kotlin.math.roundToInt

@Composable
fun ForecastDayCard(day: DailyForecast, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = day.dayLabel, style = CardTitleStyle)
                Image(
                    painter = painterResource(id = mapWeatherIcon(day.weatherIcon)),
                    contentDescription = day.description,
                    modifier = Modifier.size(32.dp)
                )
            }

            Text(text = buildTemperatureText(day.temp))
        }
    }
}

private fun buildTemperatureText(temp: Double) = buildAnnotatedString {
    withStyle(TemperatureStyle.toSpanStyle()) {
        append("${temp.roundToInt()}")
    }
    withStyle(TemperatureStyle.toSpanStyle().copy(fontSize = 18.sp)) {
        append("°")
    }
}
