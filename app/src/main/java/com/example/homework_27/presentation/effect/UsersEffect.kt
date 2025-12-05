package com.example.homework_27.presentation.effect

sealed interface UsersEffect {
    data class ShowToast(val message: String) : UsersEffect
}