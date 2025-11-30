package com.example.homework_20.presentation.common.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_20.domain.usecase.LoginUseCase
import com.example.homework_20.data.session.SessionRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionRepositoryImpl: SessionRepositoryImpl,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
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
                loginUseCase(email, password)
                if (rememberMe) {
                    sessionRepositoryImpl.saveEmail(email)
                } else {
                    sessionRepositoryImpl.clearSession()
                }
                _effect.emit(LoginEffect.NavigateToHome)
            } catch (e: Exception) {
                _effect.emit(LoginEffect.ShowToast(e.message ?: "Login failed"))
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