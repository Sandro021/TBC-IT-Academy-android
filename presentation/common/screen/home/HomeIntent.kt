package com.example.homework_20.presentation.common.screen.home

sealed class HomeIntent {
    object LoadUsers : HomeIntent()
    object GoToProfile : HomeIntent()
}