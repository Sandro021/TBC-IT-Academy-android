package com.example.homework_33.domain.repository

import com.example.homework_33.domain.common.AppResult
import com.example.homework_33.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): AppResult<User>
    suspend fun register(email: String, password: String): AppResult<User>
    suspend fun logout()
    fun currentUser(): User?
}