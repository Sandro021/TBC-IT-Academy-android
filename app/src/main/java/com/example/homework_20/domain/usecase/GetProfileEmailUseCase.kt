package com.example.homework_20.domain.usecase

import com.example.homework_20.domain.repository.SessionRepository
import javax.inject.Inject

class GetProfileEmailUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(email: String): String {
        if (email.isNotBlank()) return email
        return sessionRepository.getEmail().orEmpty()
    }
}