package com.example.homework_15.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.homework_15.R
import com.example.homework_15.orderInfo.Order
import com.example.homework_15.orderInfo.OrderStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class OrdersViewModel : ViewModel() {


    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()


    init {
        addData()
        Log.d("OrdersViewModel", "Orders loaded = ${_orders.value.size}")
    }

    private fun addData() {
        _orders.value = listOf(
            Order(
                1,
                "Modern Wingback",
                "Black",
                2,
                "$280",
                OrderStatus.COMPLETED,
                R.mipmap.softchair,
                R.drawable.black_indicator
            ),
            Order(
                2,
                "Wooden Chair",
                "brown",
                7,
                "$150",
                OrderStatus.ACTIVE,
                R.mipmap.chair,
                R.drawable.brown_indicator
            )
        )


    }


    fun markOrderAsReviewed(id: Long, reviewText: String, rating: Int) {
        _orders.value = _orders.value.map {
            if (it.id == id) it.copy(hasReview = true, reviewText = reviewText, rating = rating)
            else it
        }
    }


}

