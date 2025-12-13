package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.repository.SessionRepository
import kotlinx.coroutines.flow.first

class ShouldAutoLoginUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(): Boolean {
        val remember = sessionRepository.rememberMeFlow().first()
        return remember && sessionRepository.isFirebaseLoggedIn()
    }
}