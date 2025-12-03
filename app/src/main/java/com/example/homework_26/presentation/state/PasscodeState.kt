package com.example.homework_26.presentation.state

data class PasscodeState(
    val enteredDigits: String = "",
    val isSuccess: Boolean? = null,
    val errorMessage: String? = null
) {
    val digitsCount get() = enteredDigits.length
}