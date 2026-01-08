package com.example.homework_20.domain.usecase

import com.example.homework_20.domain.model.User
import com.example.homework_20.domain.repository.UsersRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(): List<User> {
        return usersRepository.getUsers()
    }
}