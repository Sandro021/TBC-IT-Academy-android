package com.example.gymtracker.presentation

sealed class RegisterIntent {
    data class EmailChanged(val value: String) : RegisterIntent()
    data class PasswordChanged(val value: String) : RegisterIntent()
    data class RepeatPasswordChanged(val value: String) : RegisterIntent()
    data object ClickRegister : RegisterIntent()
    data object NavigationHandled : RegisterIntent()
}