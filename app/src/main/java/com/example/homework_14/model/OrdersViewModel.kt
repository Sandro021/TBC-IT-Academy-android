package com.example.homework_14.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_14.order.OrderInfo
import com.example.homework_14.order.OrderStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class OrdersViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<OrderInfo>>(emptyList())
    val orders: StateFlow<List<OrderInfo>> = _orders


    private val _selectedStatus = MutableStateFlow(OrderStatus.PENDING)
    val selectedStatus: StateFlow<OrderStatus> = _selectedStatus


    private val _selectedOrder = MutableStateFlow<OrderInfo?>(null)
    val selectedOrder: StateFlow<OrderInfo?> = _selectedOrder

    init {
        addOrders()
    }
    private fun addOrders() {
        val currentMills = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy" , Locale.getDefault())
        dateFormat.timeZone = TimeZone.getDefault()
        val formattedDate = dateFormat.format(Date(currentMills))

        _orders.value = listOf(
            OrderInfo(1524, "IK287368838", 2, 110.0, formattedDate, OrderStatus.PENDING),
            OrderInfo(1525, "IK287318897", 3, 230.0, formattedDate, OrderStatus.PENDING),
            OrderInfo(1514, "IK287362341", 2, 110.0, formattedDate, OrderStatus.PENDING),
            OrderInfo(1679, "IK287318890", 3, 450.0, formattedDate, OrderStatus.PENDING),
            OrderInfo(1829, "IK287368831", 2, 210.0, formattedDate, OrderStatus.PENDING),
            OrderInfo(1824, "IK282918812", 3, 120.0, formattedDate, OrderStatus.PENDING)
        )

    }
    fun selectStatus(status : OrderStatus) {
        viewModelScope.launch {
            _selectedStatus.emit(status)
        }
    }
    fun selectOrder(order : OrderInfo) {
        _selectedOrder.value = order
    }
    fun updateOrderStatus(orderId: Int, newStatus: OrderStatus) {
        _orders.value = _orders.value.map { order ->
            if (order.id == orderId) order.copy(status = newStatus) else order
        }

        val current = _selectedOrder.value
        if (current != null && current.id == orderId) {
            _selectedOrder.value = current.copy(status = newStatus)
        }
    }
}