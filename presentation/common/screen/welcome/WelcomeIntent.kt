package com.example.homework_20.presentation.common.screen.welcome

sealed class WelcomeIntent {
    object CheckSession : WelcomeIntent()
    object ClickLogin : WelcomeIntent()
    object ClickRegister : WelcomeIntent()
}