package com.example.homework_37.data.remote

import com.example.homework_37.data.dto.StoryDto
import retrofit2.http.GET

interface StoriesService {
    @GET("story")
    suspend fun getStories(): List<StoryDto>
}