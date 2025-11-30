package com.example.homework_20.domain.usecase

import com.example.homework_20.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        repeatPassword: String
    ) {
        if (email.isBlank()) {
            throw IllegalArgumentException("Email cannot be empty")
        }
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        if (!emailPattern.matcher(email).matches()) {
            throw IllegalArgumentException("Invalid email format")
        }
        if (password.isBlank()) {
            throw IllegalArgumentException("Password cannot be empty")
        }
        if (password != repeatPassword) {
            throw IllegalArgumentException("Passwords do not match")
        }
        authRepository.register(email, password)
    }
}