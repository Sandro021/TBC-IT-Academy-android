package com.example.homework_33.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_33.domain.common.AppResult
import com.example.homework_33.domain.usecase.auth.LoginUseCase
import com.example.homework_33.presentation.mapper.toUiMessage
import com.example.homework_33.presentation.screen.login.contract.LoginEffect
import com.example.homework_33.presentation.screen.login.contract.LoginEvent
import com.example.homework_33.presentation.screen.login.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _effect = Channel<LoginEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()


    fun onEvent(e: LoginEvent) {
        when (e) {
            is LoginEvent.EmailChanged -> _state.update { it.copy(email = e.v, error = null) }
            is LoginEvent.PasswordChanged -> _state.update { it.copy(password = e.v, error = null) }
            LoginEvent.Submit -> login()
        }
    }

    private fun login() = viewModelScope.launch {
        val s = state.value

        if (s.email.isBlank() || s.password.isBlank()) {
            _state.update { it.copy(error = "Fields cannot be empty") }
            return@launch
        }
        _state.update { it.copy(isLoading = true, error = null) }

        when (val res = loginUseCase(s.email.trim(), s.password)) {
            is AppResult.Success -> {
                _state.update { it.copy(isLoading = false) }
                _effect.send(LoginEffect.NavigateHome)
            }

            is AppResult.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = res.error.toUiMessage()
                    )
                }
            }
        }
    }
}