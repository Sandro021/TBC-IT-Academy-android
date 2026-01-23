package com.example.exam_8.data.repository

import com.example.exam_8.data.mapper.toDomain
import com.example.exam_8.data.remote.ApiService
import com.example.exam_8.domain.repository.ChatRepository
import com.example.exam_8.domain.model.ChatUser
import javax.inject.Inject

class ChatRepositoryImpl@Inject constructor(
    private val api: ApiService
) : ChatRepository {

    override suspend fun getUsers(): List<ChatUser> {
        return api.getUsers().map { it.toDomain() }
    }
}