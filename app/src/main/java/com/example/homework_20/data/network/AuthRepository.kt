package com.example.homework_20.data.network

import com.example.homework_20.data.common.HandleResponse.safeApiCall
import com.example.homework_20.data.common.ResultWrapper
import com.example.homework_20.data.dto.LoginRequestDto
import jakarta.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi
) {
    suspend fun login(email: String, password: String): ResultWrapper<Unit> {
        return safeApiCall {
            val response = api.login(LoginRequestDto(email, password))

            if (response.isSuccessful) {
                Unit
            } else {
                throw Exception("Login failed: ${response.code()}")
            }
        }
    }

    suspend fun register(email: String, password: String): ResultWrapper<Unit> {
        return safeApiCall {
            val response = api.register(LoginRequestDto(email, password))

            if (response.isSuccessful) {
                Unit
            } else {
                throw Exception("Register failed: ${response.code()}")
            }
        }
    }
}