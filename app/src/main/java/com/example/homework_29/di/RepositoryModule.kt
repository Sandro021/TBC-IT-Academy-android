package com.example.homework_29.di

import com.example.homework_29.data.repository.LocationsRepositoryImpl
import com.example.homework_29.domain.repository.LocationsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLocationsRepository(
        impl: LocationsRepositoryImpl
    ): LocationsRepository
}