package com.example.weatherappdvt.di

import com.example.weatherappdvt.data.repository.LocationRepositoryImpl
import com.example.weatherappdvt.data.repository.WeatherRepositoryImpl
import com.example.weatherappdvt.domain.repository.LocationRepository
import com.example.weatherappdvt.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository
}
