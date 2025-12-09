package com.example.homework_28.di

import com.example.homework_28.data.network.PostApi
import com.example.homework_28.data.repository.PostRepositoryImpl
import com.example.homework_28.domain.repository.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostsRepositoryModule {

    @Provides
    @Singleton
    fun providePostsRepository(
        api: PostApi
    ): PostsRepository = PostRepositoryImpl(api)
}