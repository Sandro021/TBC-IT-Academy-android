package com.example.homework_28.domain.usecase

import com.example.homework_28.domain.model.Story
import com.example.homework_28.domain.repository.StoriesRepository

class GetStoriesUseCase(
    private val repository: StoriesRepository
) {
    suspend operator fun invoke(): List<Story> {
        return repository.getStories()
    }
}