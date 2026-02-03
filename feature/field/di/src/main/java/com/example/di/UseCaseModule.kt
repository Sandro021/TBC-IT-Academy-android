package com.example.di

import com.example.domain.repository.RegistrationRepository
import com.example.domain.usecase.GetRegistrationFormUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetRegistrationUseCase(
        repo: RegistrationRepository
    ): GetRegistrationFormUseCase = GetRegistrationFormUseCase(repo)
}