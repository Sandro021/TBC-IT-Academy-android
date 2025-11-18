package com.example.homework_20.presentation.common.screen.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_20.data.dto.LoginRequestDto
import com.example.homework_20.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<RegisterEffect>()
    val effect = _effect.asSharedFlow()

    fun processIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.EmailChanged -> updateState {
                it.copy(
                    email = intent.email,
                    isRegisterEnabled = validateInput(intent.email, it.password, it.repeatPassword)
                )
            }

            is RegisterIntent.PasswordChanged -> updateState {
                it.copy(
                    password = intent.password,
                    isRegisterEnabled = validateInput(it.email, intent.password, it.repeatPassword)
                )
            }

            is RegisterIntent.RepeatPasswordChanged -> updateState {
                it.copy(
                    repeatPassword = intent.repeatPassword,
                    isRegisterEnabled = validateInput(it.email, it.password, intent.repeatPassword)
                )
            }

            is RegisterIntent.Register -> register(
                intent.email,
                intent.password,
                intent.repeatPassword
            )
        }
    }


    private fun validateInput(email: String, password: String, repeatPassword: String): Boolean {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.isNotEmpty()
        val passwordMatch = password == repeatPassword

        return isEmailValid && isPasswordValid && passwordMatch
    }

    private fun register(email: String, password: String, repeatPassword: String) {
        viewModelScope.launch {
            if (password != repeatPassword) {
                _effect.emit(RegisterEffect.ShowToast("Passwords do not match"))
                return@launch
            }

            try {
                val response = RetrofitInstance.api.register(LoginRequestDto(email, password))
                if (response.isSuccessful) {
                    _effect.emit(RegisterEffect.ShowToast("Registered successfully"))
                    _effect.emit(RegisterEffect.NavigateToLoginWithData(email , password))
                } else {
                    _effect.emit(RegisterEffect.ShowToast("Registration failed"))
                }
            } catch (e: Exception) {
                _effect.emit(RegisterEffect.ShowToast("Error: ${e.message}"))
            }
        }
    }

    private fun updateState(reducer: (RegisterState) -> RegisterState) {
        _state.value = reducer(_state.value)
    }
}