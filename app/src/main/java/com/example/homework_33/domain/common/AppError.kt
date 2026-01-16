package com.example.homework_33.domain.common

sealed class AppError {
    data class Message(val message: String) : AppError()
    data object InvalidCredentials : AppError()
    data object UserAlreadyExists : AppError()
    data object Network : AppError()
    data object Unknown : AppError()
}