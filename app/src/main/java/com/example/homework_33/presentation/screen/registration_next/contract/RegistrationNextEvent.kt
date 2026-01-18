package com.example.homework_33.presentation.screen.registration_next.contract

sealed class RegistrationNextEvent {
    data class UsernameChanged(val username: String) : RegistrationNextEvent()
    data object SubmitRegistration : RegistrationNextEvent()
}