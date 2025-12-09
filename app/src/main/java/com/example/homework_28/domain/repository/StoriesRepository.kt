package com.example.homework_28.domain.repository

import com.example.homework_28.domain.model.Story

interface StoriesRepository {
    suspend fun getStories() : List<Story>
}