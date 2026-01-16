package com.example.homework_33.domain.usecase

import com.example.homework_33.domain.repository.AuthRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.logout()
}