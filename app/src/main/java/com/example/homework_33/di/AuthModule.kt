package com.example.homework_33.di

import com.example.homework_33.data.repository.AuthRepositoryImpl
import com.example.homework_33.data.source.AuthRemoteDataSource
import com.example.homework_33.data.source.FirebaseAuthRemoteDataSource
import com.example.homework_33.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(auth: FirebaseAuth): AuthRemoteDataSource =
        FirebaseAuthRemoteDataSource(auth)

    @Provides
    @Singleton
    fun provideAuthRepository(remote: AuthRemoteDataSource): AuthRepository =
        AuthRepositoryImpl(remote)

}