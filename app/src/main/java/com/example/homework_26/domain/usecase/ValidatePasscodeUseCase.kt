package com.example.homework_26.domain.usecase

import com.example.homework_26.domain.repository.PasscodeRepository
import javax.inject.Inject

class ValidatePasscodeUseCase @Inject constructor(
    private val repository: PasscodeRepository
) {
    operator fun invoke(input: String): Boolean {
        return input == repository.getStoredPasscode()
    }
}