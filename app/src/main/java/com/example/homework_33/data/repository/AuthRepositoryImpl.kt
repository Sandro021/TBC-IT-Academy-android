package com.example.homework_33.data.repository

import com.example.homework_33.data.mapper.mapFirebaseError
import com.example.homework_33.data.mapper.toDomain
import com.example.homework_33.data.source.AuthRemoteDataSource
import com.example.homework_33.domain.common.AppResult
import com.example.homework_33.domain.model.User
import com.example.homework_33.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val remote: AuthRemoteDataSource
) : AuthRepository {


    override suspend fun login(
        email: String,
        password: String
    ): AppResult<User> = runCatching {
        remote.login(email, password)
    }.fold(
        onSuccess = { dto -> AppResult.Success(dto.toDomain()) },
        onFailure = { e -> AppResult.Error(mapFirebaseError(e)) }
    )


    override suspend fun register(
        email: String,
        password: String
    ): AppResult<User> =
        runCatching { remote.register(email, password) }
            .fold(
                onSuccess = { dto -> AppResult.Success(dto.toDomain()) },
                onFailure = { e -> AppResult.Error(mapFirebaseError(e)) }
            )


    override suspend fun logout() {
        remote.logout()
    }

    override fun currentUser(): User? {
        return remote.currentUser()?.toDomain()

    }

}