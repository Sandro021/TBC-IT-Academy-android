package com.example.homework_28.data.repository

import com.example.homework_28.data.mapper.toDomain
import com.example.homework_28.data.network.PostApi
import com.example.homework_28.domain.model.Post
import com.example.homework_28.domain.repository.PostsRepository

class PostRepositoryImpl(
    private val api: PostApi
) : PostsRepository {
    override suspend fun getPosts(): List<Post> = api.getPosts().map { it.toDomain() }
}