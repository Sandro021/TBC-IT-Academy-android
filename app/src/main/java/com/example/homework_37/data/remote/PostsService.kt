package com.example.homework_37.data.remote

import com.example.homework_37.data.dto.PostDto
import retrofit2.http.GET

interface PostsService {
    @GET("post")
    suspend fun getFeed(): List<PostDto>
}