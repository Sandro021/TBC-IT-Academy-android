package com.example.exam_8.domain.usecase

import com.example.exam_8.domain.model.ChatUser
import com.example.exam_8.domain.repository.ChatRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repo: ChatRepository
) {
    suspend operator fun invoke(): List<ChatUser> {
        return repo.getUsers()
    }
}