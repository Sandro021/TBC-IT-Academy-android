package com.example.exam_8.domain.repository

import com.example.exam_8.domain.model.ChatUser

interface ChatRepository {
    suspend fun getUsers(): List<ChatUser>
}