package com.example.homework_29.presentation.map

sealed interface MapEffect {
    data object RequestPermission : MapEffect
    data object MoveCameraToMyLocation : MapEffect
    data class ShowError(val message: String) : MapEffect
}