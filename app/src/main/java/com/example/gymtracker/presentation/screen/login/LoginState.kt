package com.example.gymtracker.presentation.screen.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val rememberMe: Boolean = false,
    val errorMessage: String? = null,
    val navigateToHome: Boolean = false
)