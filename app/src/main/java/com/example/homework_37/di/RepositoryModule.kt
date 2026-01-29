package com.example.homework_37.di


import com.example.homework_37.data.repository.PostRepositoryImpl
import com.example.homework_37.data.repository.StoryRepositoryImpl
import com.example.homework_37.domain.repository.PostRepository
import com.example.homework_37.domain.repository.StoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindModule {
    @Binds
    @Singleton
    abstract fun bindStoriesRepository(
        impl: StoryRepositoryImpl
    ): StoryRepository

    @Binds
    @Singleton
    abstract fun bindPostsRepository(
        impl: PostRepositoryImpl
    ): PostRepository
}