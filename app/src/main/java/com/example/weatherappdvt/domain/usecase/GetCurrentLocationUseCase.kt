package com.example.weatherappdvt.domain.usecase

import com.example.weatherappdvt.domain.model.LocationCoordinates
import com.example.weatherappdvt.domain.repository.LocationRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(private val repository: LocationRepository) {
    suspend operator fun invoke(): LocationCoordinates = repository.getCurrentLocation()
}
