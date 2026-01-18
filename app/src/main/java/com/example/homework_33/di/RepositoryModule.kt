package com.example.homework_33.di

import com.example.homework_33.data.repository.EventsRepositoryImpl
import com.example.homework_33.domain.repository.EventsRepository
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
    abstract fun bindEventsRepository(
        impl: EventsRepositoryImpl
    ): EventsRepository
}