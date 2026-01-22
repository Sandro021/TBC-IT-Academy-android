package com.example.homework_35.data.repository

import com.example.homework_35.data.mapper.toDomain
import com.example.homework_35.data.remote.OrdersApi
import com.example.homework_35.domain.model.Order
import com.example.homework_35.domain.repository.OrdersRepository
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val api: OrdersApi
) : OrdersRepository {
    override suspend fun getOrders(): List<Order> {
        val response = api.getOrders()
        if (!response.success) return emptyList()
        return response.data.map { it.toDomain() }
    }
}