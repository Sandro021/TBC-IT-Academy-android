package com.example.homework_38.presentation.screen.registration.contract

sealed class RegistrationEffect {
    data class ShowToast(val message: String) : RegistrationEffect()
    data class NavigateNext(val payload: String) : RegistrationEffect()
}