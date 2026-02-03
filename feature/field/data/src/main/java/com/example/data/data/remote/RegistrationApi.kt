package com.example.data.data.remote

import com.example.data.data.dto.FieldDto
import retrofit2.http.GET

interface RegistrationApi {
    @GET("/")
    suspend fun getRegistrationFields(): List<List<FieldDto>>
}