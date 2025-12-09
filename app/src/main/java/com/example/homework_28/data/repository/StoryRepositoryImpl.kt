package com.example.homework_28.data.repository

import com.example.homework_28.data.mapper.toDomain
import com.example.homework_28.data.network.StoryApi
import com.example.homework_28.domain.model.Story
import com.example.homework_28.domain.repository.StoriesRepository

class StoryRepositoryImpl(
    private val api: StoryApi
) : StoriesRepository {
    override suspend fun getStories(): List<Story> = api.getStories().map { it.toDomain() }
}