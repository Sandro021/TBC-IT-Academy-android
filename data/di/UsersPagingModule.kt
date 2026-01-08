package com.example.homework_20.data.di

import com.example.homework_20.data.repository.UsersPagingRepositoryImpl
import com.example.homework_20.domain.repository.UsersPagingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UsersPagingModule {

    @Binds
    @Singleton
    abstract fun bindUsersPagingRepository(
        impl: UsersPagingRepositoryImpl
    ): UsersPagingRepository
}