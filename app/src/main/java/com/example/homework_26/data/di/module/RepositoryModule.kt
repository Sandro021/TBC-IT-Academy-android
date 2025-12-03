package com.example.homework_26.data.di.module

import com.example.homework_26.data.repository.PasscodeRepositoryImpl
import com.example.homework_26.domain.repository.PasscodeRepository
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
    abstract fun bindBindsPasscodeRepository(
        impl: PasscodeRepositoryImpl
    ): PasscodeRepository

}