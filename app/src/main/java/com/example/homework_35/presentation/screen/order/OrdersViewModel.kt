package com.example.homework_35.presentation.screen.order


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_35.domain.usecase.GetOrdersUseCase
import com.example.homework_35.presentation.screen.order.contract.OrdersEvent
import com.example.homework_35.presentation.screen.order.contract.OrdersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrders: GetOrdersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OrdersState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onEvent(event: OrdersEvent) {
        when (event) {
            is OrdersEvent.OnTabSelected -> _state.update {
                it.copy(selectedStatus = event.status)
            }

            OrdersEvent.Refresh -> load()
        }
    }

    private fun load() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }
        runCatching { getOrders() }
            .onSuccess { list ->
                _state.update { it.copy(isLoading = false, orders = list) }
            }
            .onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
            }
    }
}
