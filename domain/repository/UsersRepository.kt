package com.example.homework_20.domain.repository

import com.example.homework_20.domain.model.User

interface UsersRepository {
    suspend fun getUsers() : List<User>
}