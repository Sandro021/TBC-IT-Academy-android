package com.example.di


import com.example.data.data.repository.RegistrationRepositoryImpl
import com.example.domain.repository.RegistrationRepository
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
    abstract fun bindRegistrationRepository(
        impl: RegistrationRepositoryImpl
    ): RegistrationRepository
}