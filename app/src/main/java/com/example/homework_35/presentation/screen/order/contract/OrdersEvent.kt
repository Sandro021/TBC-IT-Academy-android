package com.example.homework_35.presentation.screen.order.contract

import com.example.homework_35.domain.model.OrderStatus

sealed interface OrdersEvent {
    data class OnTabSelected(val status: OrderStatus) : OrdersEvent
    data object Refresh : OrdersEvent
}