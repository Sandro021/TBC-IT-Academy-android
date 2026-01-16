package com.example.homework_33.presentation.mapper

import com.example.homework_33.domain.common.AppError

fun AppError.toUiMessage(): String = when (this) {
    AppError.InvalidCredentials -> "Invalid email or password"
    AppError.UserAlreadyExists -> "User already exists"
    AppError.Network -> "Network error. Check internet."
    AppError.Unknown -> "Something went wrong"
    is AppError.Message -> this.message
}