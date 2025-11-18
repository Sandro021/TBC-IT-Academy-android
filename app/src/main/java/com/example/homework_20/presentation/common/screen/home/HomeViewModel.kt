package com.example.homework_20.presentation.common.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_20.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadUsers -> loadUsers()
            HomeIntent.GoToProfile -> navigateToProfile()
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {

            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            try {
                val response = RetrofitInstance.api.getUsers()
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        users = response.body()?.data ?: emptyList()
                    )
                } else {
                    _state.value = _state.value.copy(isLoading = false)
                    _effect.emit(HomeEffect.ShowToast("Error loading users"))
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
                _effect.emit(HomeEffect.ShowToast("Error: ${e.message}"))
            }
        }
    }

    private fun navigateToProfile() {
        viewModelScope.launch {
            _effect.emit(HomeEffect.NavigateToProfile)
        }
    }
}