package com.example.homework_33.presentation.screen.registration_next

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_33.presentation.screen.registration_next.contract.RegistrationNextEffect
import com.example.homework_33.presentation.screen.registration_next.contract.RegistrationNextEvent
import com.example.homework_33.presentation.screen.registration_next.contract.RegistrationNextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterNextViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(RegistrationNextState())
    val state = _state.asStateFlow()

    private val effectChannel = Channel<RegistrationNextEffect>(Channel.BUFFERED)
    val effect = effectChannel.receiveAsFlow()

    fun onEvent(event: RegistrationNextEvent) {
        when (event) {
            is RegistrationNextEvent.UsernameChanged ->
                _state.update { it.copy(username = event.username, error = null) }

            RegistrationNextEvent.SubmitRegistration ->
                submit()
        }
    }

    private fun submit() {
        val username = state.value.username.trim()

        if (username.isBlank()) {
            _state.update { it.copy(error = "Username can't be empty") }
            return
        }

        viewModelScope.launch {
            effectChannel.send(RegistrationNextEffect.NavigateToHome)
        }
    }
}

