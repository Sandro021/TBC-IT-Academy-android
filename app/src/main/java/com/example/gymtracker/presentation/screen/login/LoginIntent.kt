package com.example.gymtracker.presentation.screen.login

sealed class LoginIntent {
    data class EmailChanged(val value: String) : LoginIntent()
    data class PasswordChanged(val value: String) : LoginIntent()
    data class RememberMeChanged(val value: Boolean) : LoginIntent()
    data object ClickLogin : LoginIntent()

    data object NavigationHandled : LoginIntent()
}