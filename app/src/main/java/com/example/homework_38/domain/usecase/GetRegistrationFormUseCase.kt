package com.example.homework_38.domain.usecase

import com.example.homework_38.domain.repository.RegistrationRepository
import javax.inject.Inject
import com.example.homework_38.domain.model.RegistrationField

class GetRegistrationFormUseCase @Inject constructor(
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