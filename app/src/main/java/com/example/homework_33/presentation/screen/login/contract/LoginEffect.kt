package com.example.homework_33.presentation.screen.login.contract

sealed class LoginEffect {
    data object NavigateHome : LoginEffect()
}