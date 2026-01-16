package com.example.homework_33.domain.usecase

import com.example.homework_33.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = repo.register(email, password)
}