package com.example.weatherappdvt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.weatherappdvt.presentation.weather.WeatherScreen
import com.example.weatherappdvt.ui.theme.WeatherAppDVTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppDVTTheme {
                WeatherScreen()
            }
        }
    }
}