package com.example.homework_33.presentation.screen.login.contract

sealed class LoginEvent {
    data class EmailChanged(val v: String) : LoginEvent()
    data class PasswordChanged(val v: String) : LoginEvent()
    data object Submit : LoginEvent()
}