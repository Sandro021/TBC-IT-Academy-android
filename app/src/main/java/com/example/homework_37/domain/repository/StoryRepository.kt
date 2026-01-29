package com.example.homework_37.domain.repository

import com.example.homework_37.data.common.Resource
import com.example.homework_37.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getStories(): Flow<Resource<List<Story>>>
}