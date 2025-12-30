package com.example.homework_30.presentation.screen.search.contract

sealed class SearchEffect {
    data class ShowError(val message: String) : SearchEffect()
}