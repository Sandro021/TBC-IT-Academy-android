package com.example.gymtracker.di

import com.example.gymtracker.data.repository.ExerciseGroupRepositoryImpl
import com.example.gymtracker.data.repository.ExerciseRepositoryImpl
import com.example.gymtracker.data.repository.WorkoutRepositoryImpl
import com.example.gymtracker.domain.repository.ExerciseGroupRepository
import com.example.gymtracker.domain.repository.ExerciseRepository
import com.example.gymtracker.domain.repository.WorkoutRepository
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


    @Binds
    @Singleton
    abstract fun bindExerciseRepository(
        impl: ExerciseRepositoryImpl
    ): ExerciseRepository

    @Binds
    @Singleton
    abstract fun bindWorkoutRepository(
        impl : WorkoutRepositoryImpl
    ): WorkoutRepository
}