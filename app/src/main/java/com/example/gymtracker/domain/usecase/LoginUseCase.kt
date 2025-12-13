package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.repository.AuthRepository
import java.lang.IllegalArgumentException

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val validateEmailUseCase: ValidateEmailUseCase
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password must not be empty"))
        }
        validateEmailUseCase(email).onFailure { return Result.failure(it) }
        return authRepository.login(email.trim(), password)
    }
}