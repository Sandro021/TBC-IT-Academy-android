package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.repository.SessionRepository

class LogoutUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke() = sessionRepository.logout()
}