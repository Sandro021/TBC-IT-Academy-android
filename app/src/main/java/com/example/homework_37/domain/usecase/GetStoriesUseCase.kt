package com.example.homework_37.domain.usecase

import com.example.homework_37.domain.repository.StoryRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val repository: StoryRepository
) {
    operator fun invoke() = repository.getStories()
}