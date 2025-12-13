package com.example.gymtracker.di

import com.example.gymtracker.data.FirebaseAuthRepositoryImpl
import com.example.gymtracker.domain.repository.AuthRepository
import com.example.gymtracker.domain.usecase.LoginUseCase
import com.example.gymtracker.domain.usecase.RegisterUseCase
import com.example.gymtracker.domain.usecase.ValidateEmailUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {


    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepository = FirebaseAuthRepositoryImpl(firebaseAuth)


    @Provides
    @Singleton
    fun provideValidateEmailUseCase(): ValidateEmailUseCase = ValidateEmailUseCase()

    @Provides
    fun provideLoginUseCase(
        authRepository: AuthRepository,
        validateEmailUseCase: ValidateEmailUseCase
    ): LoginUseCase = LoginUseCase(authRepository, validateEmailUseCase)

    @Provides
    fun provideRegisterUseCase(
        authRepository: AuthRepository,
        validateEmailUseCase: ValidateEmailUseCase
    ): RegisterUseCase = RegisterUseCase(authRepository, validateEmailUseCase)
}