package com.example.homework_20.data.repository

import com.example.homework_20.data.common.HandleResponse
import com.example.homework_20.data.common.ResultWrapper
import com.example.homework_20.data.dto.LoginRequestDto
import com.example.homework_20.data.mapper.toDomain
import com.example.homework_20.data.network.AuthApi
import com.example.homework_20.domain.repository.AuthRepository
import com.example.homework_20.domain.model.LoggedInUser
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun login(email: String, password: String): LoggedInUser {
        val result = HandleResponse.safeApiCall {
            val response = api.login(LoginRequestDto(email, password))

            if (response.isSuccessful) {
                response.body()?.toDomain()
                    ?: throw Exception("Invalid response")
            } else {
                throw Exception("Login failed: ${response.code()}")
            }
        }
        return when (result) {
            is ResultWrapper.Success -> result.data
            is ResultWrapper.Error -> throw Exception(result.message)
        }

    }

    override suspend fun register(email: String, password: String) {
        val result = HandleResponse.safeApiCall {
            val response = api.register(LoginRequestDto(email, password))

            if (!response.isSuccessful) {
                throw Exception("Register failed: ${response.code()}")
            }
        }
        if (result is ResultWrapper.Error) {
            throw Exception(result.message)
        }
    }
}