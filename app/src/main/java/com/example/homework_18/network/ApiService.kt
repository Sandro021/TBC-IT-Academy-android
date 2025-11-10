package com.example.homework_18.network

import com.example.homework_18.model.UserRequest
import com.example.homework_18.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header

import retrofit2.http.POST

interface ApiService {
    @POST("api/register")
    suspend fun register(
        @Body request: UserRequest,
        @Header("x-api-key") apiKey: String = "reqres-free-v1"
    ): Response<UserResponse>

    @POST("api/login")
    suspend fun login(
        @Body request: UserRequest,
        @Header("x-api-key") apiKey: String = "reqres-free-v1"
    ): Response<UserResponse>
}