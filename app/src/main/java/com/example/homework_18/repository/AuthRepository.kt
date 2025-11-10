package com.example.homework_18.repository

import com.example.homework_18.model.UserRequest
import com.example.homework_18.network.RetrofitInstance

class AuthRepository {
    suspend fun registerUser(request: UserRequest) = RetrofitInstance.api.register(request)

    suspend fun loginUser(request: UserRequest) = RetrofitInstance.api.login(request)
}