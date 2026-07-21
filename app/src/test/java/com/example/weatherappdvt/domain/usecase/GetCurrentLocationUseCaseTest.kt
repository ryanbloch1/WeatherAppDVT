package com.example.weatherappdvt.domain.usecase

import com.example.weatherappdvt.domain.model.LocationCoordinates
import com.example.weatherappdvt.domain.repository.LocationRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

private class FakeLocationRepository(
    private val location: LocationCoordinates
) : LocationRepository {
    override suspend fun getCurrentLocation(): LocationCoordinates = location
}

class GetCurrentLocationUseCaseTest {

    @Test
    fun `invoke returns location from repository`() = runTest {
        val sampleLocation = LocationCoordinates(latitude = 51.5, longitude = -0.1)
        val useCase = GetCurrentLocationUseCase(FakeLocationRepository(sampleLocation))

        val result = useCase()

        assertEquals(sampleLocation, result)
    }
}
