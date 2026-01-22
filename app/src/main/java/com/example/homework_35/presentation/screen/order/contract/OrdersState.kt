package com.example.homework_35.presentation.screen.order.contract

import com.example.homework_35.domain.model.Order
import com.example.homework_35.domain.model.OrderStatus

data class OrdersState(
    val isLoading: Boolean = false,
    val selectedStatus: OrderStatus = OrderStatus.PENDING,
    val orders: List<Order> = emptyList(),
    val error: String? = null
)
