package com.example.gymtracker.domain.usecase

import androidx.compose.runtime.retain.retain
import com.example.gymtracker.domain.repository.AuthRepository
import java.lang.IllegalArgumentException

class RegisterUseCase(
    private val authRepository: AuthRepository,
    private val validateEmailUseCase: ValidateEmailUseCase
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        repeatPassword: String
    ): Result<Unit> {
        if (email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
            return Result.failure(IllegalArgumentException("All fields must be filled"))
        }
        validateEmailUseCase(email).onFailure { return Result.failure(it) }
        if (password != repeatPassword) {
            return Result.failure(IllegalArgumentException("Password do not match"))
        }
        if (password.length < 6) {
            return Result.failure(IllegalArgumentException("Password must be at least 6 characters"))
        }
        return authRepository.register(email.trim(), password)
    }
}