package com.example.homework_27.domain.repository

import com.example.homework_27.domain.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun observeUsers(): Flow<List<User>>

    suspend fun refreshUsers() : Result<Unit>
}