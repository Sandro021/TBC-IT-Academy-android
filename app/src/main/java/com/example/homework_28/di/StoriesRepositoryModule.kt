package com.example.homework_28.di

import com.example.homework_28.data.network.StoryApi
import com.example.homework_28.data.repository.StoryRepositoryImpl
import com.example.homework_28.domain.repository.StoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object StoriesRepositoryModule {


    @Provides
    @Singleton
    fun provideStoriesRepository(api: StoryApi): StoriesRepository = StoryRepositoryImpl(api)
}