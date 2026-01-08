package com.example.homework_20.domain.repository

interface SessionRepository {
    suspend fun saveEmail(email: String)
    suspend fun clearSession()
    suspend fun isLoggedIn(): Boolean
    suspend fun getEmail(): String?
}