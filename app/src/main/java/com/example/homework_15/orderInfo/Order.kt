package com.example.homework_15.orderInfo

import androidx.annotation.DrawableRes

data class Order(
    val id: Long,
    val title: String,
    val color: String,
    val quantity: Int,
    val price: String,
    val status: OrderStatus,
    val imageResId: Int,
    @DrawableRes val indicator : Int,
    val hasReview: Boolean = false,
    val reviewText: String? = null,
    val rating: Int = 0
)

enum class OrderStatus {ACTIVE , COMPLETED}
