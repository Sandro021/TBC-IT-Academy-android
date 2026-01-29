package com.example.homework_37.presentation.screen.home.contract

sealed class HomeIntent {
    data object LoadData : HomeIntent()

}