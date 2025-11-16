package com.example.homework_20.data.network

import com.example.homework_20.data.dto.LoginRequestDto
import com.example.homework_20.data.dto.LoginResponseDto
import com.example.homework_20.data.dto.RegisterResponseDto
import com.example.homework_20.data.dto.UsersResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequestDto,
        @Header("x-api-key") apiKey: String = "reqres-free-v1"
    ): Response<LoginResponseDto>


    @POST("api/register")
    suspend fun register(
        @Body request: LoginRequestDto,
        @Header("x-api-key") apiKey: String = "reqres-free-v1"
    ): Response<RegisterResponseDto>

    @GET("api/users")
    suspend fun getUsers(@Query("page") page: Int = 1): Response<UsersResponseDto>
}