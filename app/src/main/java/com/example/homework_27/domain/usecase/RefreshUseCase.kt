package com.example.homework_27.domain.usecase

import com.example.homework_27.domain.repository.UsersRepository
import javax.inject.Inject

class RefreshUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.refreshUsers()
}