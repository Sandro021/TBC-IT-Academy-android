package com.example.homework_20.presentation.common.screen.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val errorMessage: String? = null,
    val isRegisterEnabled: Boolean = false,
    val isRegistered: Boolean = false
)