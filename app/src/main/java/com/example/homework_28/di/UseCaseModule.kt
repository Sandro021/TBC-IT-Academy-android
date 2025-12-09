package com.example.homework_28.di

import com.example.homework_28.domain.repository.PostsRepository
import com.example.homework_28.domain.repository.StoriesRepository
import com.example.homework_28.domain.usecase.GetPostsUseCase
import com.example.homework_28.domain.usecase.GetStoriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun providePostsUseCase(repository: PostsRepository): GetPostsUseCase =
        GetPostsUseCase(repository)

    @Provides
    fun provideStoriesUseCase(repository: StoriesRepository): GetStoriesUseCase =
        GetStoriesUseCase(repository)
}