package com.example.weatherappdvt.presentation.forecast

import com.example.weatherappdvt.domain.model.Forecast

sealed interface ForecastUiState {
    data object PermissionRequired : ForecastUiState
    data object Loading : ForecastUiState
    data class Success(val forecast: Forecast) : ForecastUiState
    data class Error(val message: String) : ForecastUiState
}
