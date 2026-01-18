package com.example.homework_33.presentation.screen.registration.contract

sealed class RegistrationEffect {
    data object NavigateToNext : RegistrationEffect()
}