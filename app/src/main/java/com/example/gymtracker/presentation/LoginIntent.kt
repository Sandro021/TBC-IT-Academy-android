package com.example.gymtracker.presentation

sealed class LoginIntent {
    data class EmailChanged(val value: String) : LoginIntent()
    data class PasswordChanged(val value: String) : LoginIntent()
    data object ClickLogin : LoginIntent()
    data object NavigationHandled : LoginIntent()
}