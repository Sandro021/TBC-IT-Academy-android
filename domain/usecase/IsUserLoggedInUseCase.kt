package com.example.homework_20.domain.usecase

import com.example.homework_20.domain.repository.SessionRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(): Boolean {
        return sessionRepository.isLoggedIn()
    }
}