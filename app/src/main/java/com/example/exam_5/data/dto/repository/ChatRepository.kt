package com.example.exam_5.data.dto.repository

import com.example.exam_5.data.dto.ChatItemDto
import com.example.exam_5.data.dto.network.ChatApiService
import javax.inject.Inject

class ChatRepository@Inject constructor(
    private val api : ChatApiService
) {
    suspend fun loadChats() : List<ChatItemDto> {
        return api.getChats()
    }
}