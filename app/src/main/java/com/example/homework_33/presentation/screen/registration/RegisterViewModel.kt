package com.example.homework_33.presentation.screen.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_33.domain.common.AppResult
import com.example.homework_33.domain.usecase.auth.RegisterUseCase
import com.example.homework_33.presentation.screen.registration.contract.RegistrationEffect
import com.example.homework_33.presentation.screen.registration.contract.RegistrationEvent
import com.example.homework_33.presentation.screen.registration.contract.RegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val effectChannel = Channel<RegistrationEffect>(Channel.BUFFERED)
    val effect = effectChannel.receiveAsFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.EmailChanged ->
                _state.update { it.copy(email = event.v, error = null) }

            is RegistrationEvent.PasswordChanged ->
                _state.update { it.copy(password = event.v, error = null) }

            RegistrationEvent.Submit -> register()
        }
    }

    private fun goNext() {
        val email = state.value.email.trim()
        val password = state.value.password

        if (email.isBlank() || password.isBlank()) {
            _state.update { it.copy(error = "Email and password can't be empty") }
            return
        }
        viewModelScope.launch { effectChannel.send(RegistrationEffect.NavigateToNext) }
    }

    private fun register() {
        val email = state.value.email.trim()
        val password = state.value.password

        if (email.isBlank() || password.isBlank()) {
            _state.update { it.copy(error = "Email and password can't be empty") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val res = registerUseCase(email, password)) {
                is AppResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    effectChannel.send(RegistrationEffect.NavigateToNext)
                }

                is AppResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = res.error.toString()
                        )
                    }
                }
            }
        }
    }
}
