package com.example.homework_20.data.repository

import com.example.homework_20.data.common.HandleResponse
import com.example.homework_20.data.common.ResultWrapper
import com.example.homework_20.data.mapper.toDomain
import com.example.homework_20.data.network.AuthApi
import com.example.homework_20.domain.model.User
import com.example.homework_20.domain.repository.UsersRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : UsersRepository {
    override suspend fun getUsers(): List<User> {
        val result = HandleResponse.safeApiCall {
            val response = api.getUsers()

            if (!response.isSuccessful) {
                throw Exception("Failed to load users: ${response.code()}")
            }

            response.body()?.data ?: emptyList()
        }
        return when (result) {
            is ResultWrapper.Success -> result.data.map { it.toDomain() }
            is ResultWrapper.Error -> throw Exception(result.message)
        }
    }
}