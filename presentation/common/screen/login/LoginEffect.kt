package com.example.homework_20.presentation.common.screen.login

sealed class LoginEffect {
    object NavigateToHome : LoginEffect()
    data class ShowToast(val message: String) : LoginEffect()
}
