package com.example.homework_33.presentation.screen.registration_next.contract

sealed class RegistrationNextEffect {
    data object NavigateToHome : RegistrationNextEffect()
}