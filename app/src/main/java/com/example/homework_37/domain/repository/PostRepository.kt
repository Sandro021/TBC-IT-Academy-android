package com.example.homework_37.domain.repository

import com.example.homework_37.data.common.Resource
import com.example.homework_37.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getFeed(): Flow<Resource<List<Post>>>
}