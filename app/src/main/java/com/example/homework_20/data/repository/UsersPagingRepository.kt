package com.example.homework_20.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.homework_20.data.dto.UserDto
import com.example.homework_20.data.network.AuthApi
import com.example.homework_20.data.paging.UsersPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersPagingRepository @Inject constructor(
    private val api: AuthApi
) {
    fun getUsersPaging(): Flow<PagingData<UserDto>> {
        return Pager(
            config = PagingConfig(pageSize = 6, enablePlaceholders = false),
            pagingSourceFactory = { UsersPagingSource(api) }
        ).flow
    }
}