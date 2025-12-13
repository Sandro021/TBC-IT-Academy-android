package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.repository.SessionRepository

class SetRememberMeUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(value: Boolean) = sessionRepository.setRememberMe(value)
}