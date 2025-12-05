package com.example.homework_27.data.repository

import com.example.homework_27.data.UsersApi
import com.example.homework_27.data.local.dao.UsersDao
import com.example.homework_27.data.mapper.toDomain
import com.example.homework_27.data.mapper.toEntity
import com.example.homework_27.data.network.NetworkMonitor
import com.example.homework_27.domain.User
import com.example.homework_27.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.IllegalStateException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi,
    private val dao: UsersDao,
    private val networkMonitor: NetworkMonitor
) : UsersRepository {
    override fun observeUsers(): Flow<List<User>> = dao.observeUsers().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun refreshUsers(): Result<Unit> {
        return try {
            val online = networkMonitor.isOnline()
            if (!online) {
                return Result.failure(IllegalStateException("Offline"))
            }
            val remoteUsers = api.getUsers()

            for (dto in remoteUsers) {
                try {
                    val entity = dto.toEntity().copy(saveError = null)
                    dao.insertUsers(listOf(entity))
                } catch (e: Exception) {
                    val entityWithError =
                        dto.toEntity().copy(saveError = e.message ?: "Save failed")
                }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}