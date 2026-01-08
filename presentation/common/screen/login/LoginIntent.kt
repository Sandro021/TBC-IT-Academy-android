package com.example.homework_20.presentation.common.screen.login

sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    data class Login(val email: String, val password: String, val rememberMe: Boolean) :
        LoginIntent()

    data class FillAfterRegistration(val email: String, val password: String) : LoginIntent()
}