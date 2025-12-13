package com.example.gymtracker.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun rememberMeFlow(): Flow<Boolean>
    suspend fun setRememberMe(value: Boolean)

    fun isFirebaseLoggedIn(): Boolean
    suspend fun logout()
}