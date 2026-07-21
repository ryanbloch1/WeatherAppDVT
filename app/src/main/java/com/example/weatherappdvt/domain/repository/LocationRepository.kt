package com.example.weatherappdvt.domain.repository

import com.example.weatherappdvt.domain.model.LocationCoordinates

interface LocationRepository {
    suspend fun getCurrentLocation(): LocationCoordinates
}
