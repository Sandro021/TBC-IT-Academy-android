package com.example.homework_20.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.homework_20.data.mapper.toDomain
import com.example.homework_20.data.network.AuthApi
import com.example.homework_20.data.paging.UsersPagingSource
import com.example.homework_20.domain.model.User
import com.example.homework_20.domain.repository.UsersPagingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersPagingRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : UsersPagingRepository {
    override fun getUsersPaging(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 6, enablePlaceholders = false),
            pagingSourceFactory = { UsersPagingSource(api) }
        ).flow.map { pagingData -> pagingData.map { dto -> dto.toDomain() } }
    }
}