package com.example.domain.usecase

import com.example.domain.repository.RegistrationRepository
import com.example.domain.model.RegistrationField

class GetRegistrationFormUseCase(
    private val repository: RegistrationRepository
) {
    suspend operator fun invoke(): Result<List<List<RegistrationField>>> {
        return try {
            Result.success(repository.getFormStructure())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}