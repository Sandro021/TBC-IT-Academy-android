package com.example.homework_20.presentation.common.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_20.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<RegisterEffect>()
    val effect = _effect.asSharedFlow()

    fun processIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.EmailChanged -> updateState {
                it.copy(
                    email = intent.email,
                    isRegisterEnabled = shouldEnableRegisterButton(
                        intent.email,
                        it.password,
                        it.repeatPassword
                    )
                )
            }

            is RegisterIntent.PasswordChanged -> updateState {
                it.copy(
                    password = intent.password,
                    isRegisterEnabled = shouldEnableRegisterButton(
                        it.email,
                        intent.password,
                        it.repeatPassword
                    )
                )
            }

            is RegisterIntent.RepeatPasswordChanged -> updateState {
                it.copy(
                    repeatPassword = intent.repeatPassword,
                    isRegisterEnabled = shouldEnableRegisterButton(
                        it.email,
                        it.password,
                        intent.repeatPassword
                    )
                )
            }

            is RegisterIntent.Register -> register(
                intent.email,
                intent.password,
                intent.repeatPassword
            )
        }
    }


    private fun shouldEnableRegisterButton(
        email: String,
        password: String,
        repeatPassword: String
    ): Boolean {
        val isEmailNotBlank = email.isNotBlank()
        val isPasswordNotBlank = password.isNotBlank()
        val passwordsMatch = password == repeatPassword

        return isEmailNotBlank && isPasswordNotBlank && passwordsMatch
    }

    private fun register(email: String, password: String, repeatPassword: String) {
        viewModelScope.launch {
            try {
                registerUseCase(email, password, repeatPassword)

                _effect.emit(RegisterEffect.ShowToast("Registered successfully"))
                _effect.emit(RegisterEffect.NavigateToLoginWithData(email, password))
            } catch (e: IllegalArgumentException) {
                _effect.emit(RegisterEffect.ShowToast(e.message ?: "Invalid input"))
            } catch (e: Exception) {
                _effect.emit(RegisterEffect.ShowToast("Register failed: ${e.message}"))
            }
        }
    }

    private fun updateState(reducer: (RegisterState) -> RegisterState) {
        _state.value = reducer(_state.value)
    }
}