package com.example.homework_20.domain.repository

import com.example.homework_20.domain.model.LoggedInUser

interface AuthRepository {
    suspend fun login(email: String, password: String): LoggedInUser
    suspend fun register(email: String, password: String)
}