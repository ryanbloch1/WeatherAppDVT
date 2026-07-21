package com.example.weatherappdvt.presentation.forecast

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherappdvt.domain.model.Forecast
import com.example.weatherappdvt.ui.theme.ForecastTitleStyle

@Composable
fun ForecastScreen(viewModel: ForecastViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            viewModel.onLocationPermissionGranted()
        } else {
            viewModel.onLocationPermissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val successState = uiState as? ForecastUiState.Success
        if (successState != null) {
            WeatherBackground(condition = successState.forecast.condition)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            )
        }

        when (val state = uiState) {
            is ForecastUiState.PermissionRequired -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            is ForecastUiState.Loading -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    ForecastTitleBar()
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            }

            is ForecastUiState.Success -> {
                ForecastContent(forecast = state.forecast)
            }

            is ForecastUiState.Error -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    ForecastTitleBar()
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun ForecastContent(forecast: Forecast) {
    Column(modifier = Modifier.fillMaxSize()) {
        ForecastTitleBar()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            items(forecast.days) { day ->
                ForecastDayCard(day = day)
            }
        }
    }
}

@Composable
private fun ForecastTitleBar() {
    Column(modifier = Modifier.statusBarsPadding()) {
        Text(
            text = "5 Day Forecast",
            style = ForecastTitleStyle,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 32.dp, bottom = 16.dp)
        )
        HorizontalDivider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp)
    }
}
