package com.example.homework_28.data.network

import com.example.homework_28.data.model.StoryDto
import retrofit2.http.GET

interface StoryApi {

    @GET("0f76d541-3832-4a3c-927a-0593e060d6da")
    suspend fun getStories(): List<StoryDto>
}