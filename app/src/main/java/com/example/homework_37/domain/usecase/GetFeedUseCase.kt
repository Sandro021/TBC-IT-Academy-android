package com.example.homework_37.domain.usecase

import com.example.homework_37.domain.repository.PostRepository
import javax.inject.Inject

class GetFeedUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke() = repository.getFeed()
}