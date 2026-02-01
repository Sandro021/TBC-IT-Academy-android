package com.example.homework_38.presentation.screen.registration.contract

sealed class RegistrationEvent {
    object LoadFields : RegistrationEvent()
    data class OnValueChange(val fieldId: Int, val value: String) : RegistrationEvent()
    object OnRegisterClicked : RegistrationEvent()
}