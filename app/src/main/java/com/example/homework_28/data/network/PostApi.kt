package com.example.homework_28.data.network

import com.example.homework_28.data.model.PostDto
import retrofit2.http.GET

interface PostApi {
    @GET("1e3f40b1-19a5-4986-ad60-fdc80c27234b")
    suspend fun getPosts(): List<PostDto>
}