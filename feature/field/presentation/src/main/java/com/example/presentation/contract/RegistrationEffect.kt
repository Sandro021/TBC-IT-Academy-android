package com.example.presentation.contract

sealed class RegistrationEffect {
    data class ShowSnackbar(val message: String) : RegistrationEffect()
}