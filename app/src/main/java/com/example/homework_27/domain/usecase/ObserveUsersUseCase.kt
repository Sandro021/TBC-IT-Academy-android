package com.example.homework_27.domain.usecase

import com.example.homework_27.domain.User
import com.example.homework_27.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUsersUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    operator fun invoke(): Flow<List<User>> = repository.observeUsers()
}