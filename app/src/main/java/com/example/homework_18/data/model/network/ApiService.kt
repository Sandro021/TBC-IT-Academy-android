package com.example.homework_18.data.model.network

import com.example.homework_18.data.model.UserRequestDto
import com.example.homework_18.data.model.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/register")
    suspend fun register(
        @Body request: UserRequestDto,
        @Header("x-api-key") apiKey: String = "reqres-free-v1"
    ): Response<UserResponseDto>

    @POST("api/login")
    suspend fun login(
        @Body request: UserRequestDto,
        @Header("x-api-key") apiKey: String = "reqres-free-v1"
    ): Response<UserResponseDto>
}