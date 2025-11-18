package com.example.homework_20.presentation.common.screen.register

sealed class RegisterEffect {
    data class ShowToast(val message: String) : RegisterEffect()
    data class NavigateToLoginWithData(val email: String, val password: String) : RegisterEffect()
}