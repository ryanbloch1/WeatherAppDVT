package com.example.weatherappdvt.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.weatherappdvt.domain.model.LocationCoordinates
import com.example.weatherappdvt.domain.repository.LocationRepository
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): LocationCoordinates =
        suspendCancellableCoroutine { continuation ->
            val cancellationTokenSource = CancellationTokenSource()
            val client = LocationServices.getFusedLocationProviderClient(context)

            continuation.invokeOnCancellation { cancellationTokenSource.cancel() }

            client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        continuation.resume(
                            LocationCoordinates(latitude = location.latitude, longitude = location.longitude)
                        )
                    } else {
                        client.lastLocation
                            .addOnSuccessListener { lastLocation ->
                                if (lastLocation != null) {
                                    continuation.resume(
                                        LocationCoordinates(
                                            latitude = lastLocation.latitude,
                                            longitude = lastLocation.longitude
                                        )
                                    )
                                } else {
                                    continuation.resumeWithException(
                                        IllegalStateException("Unable to determine current location")
                                    )
                                }
                            }
                            .addOnFailureListener { exception ->
                                continuation.resumeWithException(exception)
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
}
