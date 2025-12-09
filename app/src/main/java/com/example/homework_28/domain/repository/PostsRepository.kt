package com.example.homework_28.domain.repository

import com.example.homework_28.domain.model.Post

interface PostsRepository {
    suspend fun getPosts(): List<Post>
}