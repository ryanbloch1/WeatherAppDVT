package com.example.weatherappdvt.presentation.forecast

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.weatherappdvt.R
import com.example.weatherappdvt.domain.model.WeatherCondition

@Composable
fun weatherBackgroundRes(condition: WeatherCondition): Int =
    when (condition) {
        WeatherCondition.SUNNY -> R.drawable.bg_sunny
        WeatherCondition.CLOUDY -> R.drawable.bg_cloudy
        WeatherCondition.RAINY -> R.drawable.bg_rainy
    }

@Composable
fun WeatherBackground(condition: WeatherCondition, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = weatherBackgroundRes(condition)),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize()
    )
}
