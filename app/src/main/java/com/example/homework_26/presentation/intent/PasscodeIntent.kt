package com.example.homework_26.presentation.intent

sealed class PasscodeIntent {
    data class NumberClicked(val digit: Int) : PasscodeIntent()
    data object DeleteClicked : PasscodeIntent()
}