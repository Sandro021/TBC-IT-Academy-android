package com.example.homework_35.domain.usecase

import com.example.homework_35.domain.model.Order
import com.example.homework_35.domain.repository.OrdersRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repo: OrdersRepository
) {
    suspend operator fun invoke(): List<Order> = repo.getOrders()
}