package com.example.gymtracker.domain.usecase

import java.lang.IllegalArgumentException

class ValidateEmailUseCase {
    operator fun invoke(email: String): Result<Unit> {
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS

        return if (emailPattern.matcher(email).matches()) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Invalid email format"))
        }
    }
}