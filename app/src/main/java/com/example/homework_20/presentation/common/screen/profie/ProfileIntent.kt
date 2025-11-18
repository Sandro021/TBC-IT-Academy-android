package com.example.homework_20.presentation.common.screen.profie

sealed class ProfileIntent {
    data class LoadProfile(val email: String) : ProfileIntent()
    object Logout : ProfileIntent()
}