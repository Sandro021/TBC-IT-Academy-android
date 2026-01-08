package com.example.homework_20.domain.usecase

import com.example.homework_20.domain.repository.SessionRepository
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke() {
        sessionRepository.clearSession()
    }
}