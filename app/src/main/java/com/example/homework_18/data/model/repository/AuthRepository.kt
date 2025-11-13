package com.example.homework_18.data.model.repository

import com.example.homework_18.data.model.UserRequestDto
import com.example.homework_18.data.model.network.RetrofitInstance

class AuthRepository {
    suspend fun registerUser(request: UserRequestDto) = RetrofitInstance.api.register(request)

    suspend fun loginUser(request: UserRequestDto) = RetrofitInstance.api.login(request)
}