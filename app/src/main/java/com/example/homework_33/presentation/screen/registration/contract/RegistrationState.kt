package com.example.homework_33.presentation.screen.registration.contract

data class RegistrationState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)