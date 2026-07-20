package com.example.weatherappdvt.presentation.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherappdvt.domain.model.Weather

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.loadWeather(latitude = 51.5, longitude = -0.1)
    }

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Weather") })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState.value) {
                is WeatherUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is WeatherUiState.Success -> {
                    WeatherContent(state.weather)
                }
                is WeatherUiState.Error -> {
                    Text("Error: ${state.message}")
                }
            }
        }
    }
}

@Composable
fun WeatherContent(weather: Weather) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = weather.timezone, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${weather.temp}°C", fontSize = 40.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = weather.description, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Feels like: ${weather.feelsLike}°C")
        Text(text = "Humidity: ${weather.humidity}%")
        Text(text = "Wind speed: ${weather.windSpeed} m/s")
        Text(text = "Pressure: ${weather.pressure} hPa")
    }
}
