package com.example.homework_28.domain.usecase

import com.example.homework_28.domain.model.Post
import com.example.homework_28.domain.repository.PostsRepository

class GetPostsUseCase(
    private val repository: PostsRepository
) {
    suspend operator fun invoke(): List<Post> {
        return repository.getPosts()
    }
}