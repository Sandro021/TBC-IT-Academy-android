package com.example.homework_27.data.module

import com.example.homework_27.data.repository.UsersRepositoryImpl
import com.example.homework_27.domain.repository.UsersRepository
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
    abstract fun bindUsersRepository(
        impl: UsersRepositoryImpl
    ): UsersRepository
}