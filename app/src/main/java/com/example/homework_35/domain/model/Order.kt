package com.example.homework_35.domain.model

import java.time.LocalDate

data class Order(
    val orderId: Int,
    val trackingNumber: String,
    val quantity: Int,
    val status: OrderStatus,
    val date: LocalDate,
    val subtotal: Int
)

enum class OrderStatus { PENDING, DELIVERED, CANCELLED }