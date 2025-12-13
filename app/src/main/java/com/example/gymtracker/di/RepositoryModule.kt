package com.example.gymtracker.di

import com.example.gymtracker.data.firestore.ExerciseGroupRepositoryImpl
import com.example.gymtracker.domain.repository.ExerciseGroupRepository
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
    abstract fun bindExerciseGroupRepository(
        impl: ExerciseGroupRepositoryImpl
    ): ExerciseGroupRepository
}