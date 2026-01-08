package com.example.homework_20.presentation.common.screen.register

sealed class RegisterIntent {
    data class EmailChanged(val email: String) : RegisterIntent()
    data class PasswordChanged(val password: String) : RegisterIntent()
    data class RepeatPasswordChanged(val repeatPassword: String) : RegisterIntent()

    data class Register(val email: String, val password: String, val repeatPassword: String) :
        RegisterIntent()

}