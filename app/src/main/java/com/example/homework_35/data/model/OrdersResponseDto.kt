package com.example.homework_35.data.model

import kotlinx.serialization.Serializable

@Serializable
data class OrdersResponseDto(
    val data: List<OrderDto>,
    val success: Boolean
)