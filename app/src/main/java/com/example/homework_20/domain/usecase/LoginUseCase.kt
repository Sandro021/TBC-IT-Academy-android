package com.example.homework_20.domain.usecase

import com.example.homework_20.domain.repository.AuthRepository
import com.example.homework_20.domain.model.LoggedInUser
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): LoggedInUser {
        if (email.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Email or password empty")
        }
        return repository.login(email, password)
    }
}