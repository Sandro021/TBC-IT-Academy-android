package com.example.gymtracker.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtracker.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()


    fun processIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.EmailChanged ->
                _state.update { it.copy(email = intent.value, errorMessage = null) }

            is RegisterIntent.PasswordChanged ->
                _state.update { it.copy(password = intent.value, errorMessage = null) }

            is RegisterIntent.RepeatPasswordChanged ->
                _state.update { it.copy(repeatPassword = intent.value, errorMessage = null) }

            RegisterIntent.ClickRegister -> register()

            RegisterIntent.NavigationHandled ->
                _state.update { it.copy(navigateToHome = false) }
        }
    }

    private fun register() {
        val current = _state.value

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val result = registerUseCase(current.email, current.password, current.repeatPassword)

            result.onSuccess {
                _state.update {
                    it.copy(isLoading = false, navigateToHome = true, errorMessage = null)
                }
            }.onFailure { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Registration failed"
                    )
                }
            }
        }
    }
}