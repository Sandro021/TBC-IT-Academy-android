package com.example.challenge.presentation.screen.log_in.contract


sealed interface LoginEvent {

    data class LogIn(val email: String, val password: String) : LoginEvent

    object ResetErrorMessage : LoginEvent
}