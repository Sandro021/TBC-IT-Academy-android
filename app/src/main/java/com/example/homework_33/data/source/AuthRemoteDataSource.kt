package com.example.homework_33.data.source

import com.example.homework_33.data.model.UserDto

interface AuthRemoteDataSource {
    suspend fun login(email: String, password: String): UserDto
    suspend fun register(email: String, password: String): UserDto
    fun currentUser(): UserDto?
    fun logout()
}