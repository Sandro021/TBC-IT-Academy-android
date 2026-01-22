package com.example.homework_35.data.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val orderId: Int,
    val trackingNumber: String,
    val quantity: Int,
    val status: String,
    val date: String,
    val subtotal: Int
)