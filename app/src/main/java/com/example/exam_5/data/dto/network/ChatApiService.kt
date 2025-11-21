package com.example.exam_5.data.dto.network

import com.example.exam_5.data.dto.ChatItemDto
import retrofit2.http.GET

interface ChatApiService {
    @GET("d7d9436b-21c5-43f7-82f9-2334163351cf")
    suspend fun getChats(): List<ChatItemDto>
}