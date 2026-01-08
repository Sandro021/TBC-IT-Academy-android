package com.example.homework_20.presentation.common.screen.home

sealed class HomeEffect {
    data class ShowToast(val message: String) : HomeEffect()
    object NavigateToProfile : HomeEffect()
}