package com.example.homework_20.data.network

import com.example.homework_20.data.common.HandleResponse.safeApiCall
import com.example.homework_20.data.common.ResultWrapper
import com.example.homework_20.data.dto.UserDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val api: AuthApi
) {
    suspend fun getUsers(): ResultWrapper<List<UserDto>> {
        return safeApiCall {
            val response = api.getUsers()

            if (!response.isSuccessful) {
                throw Exception("Failed to load users: ${response.code()}")
            }

            response.body()?.data ?: emptyList()
        }
    }
}