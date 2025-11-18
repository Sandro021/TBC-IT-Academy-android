package com.example.homework_20.presentation.common.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_20.data.dto.LoginRequestDto
import com.example.homework_20.data.network.RetrofitInstance
import com.example.homework_20.presentation.common.screen.sessionRepository.SessionRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val sessionRepository: SessionRepository) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect = _effect.asSharedFlow()


    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> updateState {
                it.copy(
                    email = intent.email,
                    isLoginEnabled = validateInput(intent.email, it.password)
                )
            }

            is LoginIntent.PasswordChanged -> updateState {
                it.copy(
                    password = intent.password,
                    isLoginEnabled = validateInput(it.email, intent.password)
                )
            }

            is LoginIntent.Login -> login(intent.email, intent.password, intent.rememberMe)
            is LoginIntent.FillAfterRegistration -> updateState {
                it.copy(
                    email = intent.email,
                    password = intent.password,
                    isLoginEnabled = validateInput(intent.email, intent.password)
                )
            }
        }
    }

    private fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.login(LoginRequestDto(email, password))
                if (response.isSuccessful) {
                    updateState { it.copy(email = email) }
                    if (rememberMe) {
                        sessionRepository.saveEmail(email)
                    } else {
                        sessionRepository.clearSession()
                    }
                    _effect.emit(LoginEffect.NavigateToHome)
                } else {
                    _effect.emit(LoginEffect.ShowToast("Login failed"))
                }
            } catch (e: Exception) {
                _effect.emit(LoginEffect.ShowToast("Error: ${e.message}"))
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotEmpty()
    }

    private fun updateState(reducer: (LoginState) -> LoginState) {
        _state.value = reducer(_state.value)
    }

}