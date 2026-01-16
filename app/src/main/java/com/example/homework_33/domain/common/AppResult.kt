package com.example.homework_33.domain.common

sealed class AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>()
    data class Error<T>(val error: AppError) : AppResult<T>()
}