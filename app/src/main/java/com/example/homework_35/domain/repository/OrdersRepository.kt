package com.example.homework_35.domain.repository

import com.example.homework_35.domain.model.Order

interface OrdersRepository {
    suspend fun getOrders() : List<Order>
}