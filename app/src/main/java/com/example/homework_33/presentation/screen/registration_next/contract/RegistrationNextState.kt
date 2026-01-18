package com.example.homework_33.presentation.screen.registration_next.contract

data class RegistrationNextState(
    val username: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
