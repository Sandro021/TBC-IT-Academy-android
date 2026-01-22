package com.example.homework_35.data.remote

import com.example.homework_35.data.model.OrdersResponseDto
import retrofit2.http.GET

interface OrdersApi {
    @GET("/")
    suspend fun getOrders(): OrdersResponseDto
}