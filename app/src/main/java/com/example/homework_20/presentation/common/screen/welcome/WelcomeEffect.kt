package com.example.homework_20.presentation.common.screen.welcome

sealed class WelcomeEffect {
    object NavigateToHome : WelcomeEffect()
    object NavigateToLogin : WelcomeEffect()
    object NavigateToRegister : WelcomeEffect()
}
