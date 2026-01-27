package com.example.homework_36.di

import com.example.homework_36.data.repository.TourRepositoryImpl
import com.example.homework_36.domain.repository.TourRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ToursBindModule {
    @Binds
    @Singleton
    abstract fun bindOrdersRepository(
        impl: TourRepositoryImpl
    ): TourRepository
}