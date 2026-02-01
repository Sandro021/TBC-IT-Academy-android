package com.example.homework_38.data.remote

import com.example.homework_38.data.dto.FieldDto
import retrofit2.http.GET

interface RegistrationApi {
    @GET("/")
    suspend fun getRegistrationFields(): List<List<FieldDto>>
}