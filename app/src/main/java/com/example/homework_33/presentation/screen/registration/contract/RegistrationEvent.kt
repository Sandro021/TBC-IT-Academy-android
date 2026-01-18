package com.example.homework_33.presentation.screen.registration.contract

sealed class RegistrationEvent {
    data class EmailChanged(val v: String) : RegistrationEvent()
    data class PasswordChanged(val v: String) : RegistrationEvent()
    data object Submit : RegistrationEvent()
}