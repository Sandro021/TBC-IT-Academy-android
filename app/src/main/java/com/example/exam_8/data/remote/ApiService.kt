package com.example.exam_8.data.remote

import com.example.exam_8.data.model.ChatUserDto
import retrofit2.http.GET

interface ApiService {
    @GET("/chats")
    suspend fun getUsers(): List<ChatUserDto>
}