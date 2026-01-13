package com.example.homework_32.presentation.screen.main.contract

sealed class MainEffect {
    data class ShowMessage(val text: String) : MainEffect()
}