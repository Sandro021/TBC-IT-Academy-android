package com.example.homework_27.data

import com.example.homework_27.data.model.UserDto
import retrofit2.http.GET

interface UsersApi {
    @GET("3668d139-e182-4fe2-b909-6259524117cb")
    suspend fun getUsers(): List<UserDto>
}