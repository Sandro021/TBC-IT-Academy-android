package com.example.homework_20.presentation.common.screen.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoginEnabled: Boolean = false,
    val error: String? = null
)
