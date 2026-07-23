package com.example.weatherappdvt.presentation.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappdvt.domain.usecase.GetCurrentLocationUseCase
import com.example.weatherappdvt.domain.usecase.GetForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ForecastUiState>(ForecastUiState.PermissionRequired)
    val uiState: StateFlow<ForecastUiState> = _uiState

    // Intentional UI error boundary: any failure (location, network, parsing) surfaces as ForecastUiState.Error
    @Suppress("TooGenericExceptionCaught")
    fun onLocationPermissionGranted() {
        viewModelScope.launch {
            _uiState.value = ForecastUiState.Loading
            try {
                val location = getCurrentLocationUseCase()
                val forecast = getForecastUseCase(location.latitude, location.longitude)
                _uiState.value = ForecastUiState.Success(forecast)
            } catch (e: Exception) {
                _uiState.value = ForecastUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun onLocationPermissionDenied() {
        _uiState.value = ForecastUiState.Error("Location permission is required to show the forecast")
    }
}
