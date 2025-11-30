package com.example.homework_20.domain.repository

import androidx.paging.PagingData
import com.example.homework_20.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersPagingRepository {
    fun getUsersPaging(): Flow<PagingData<User>>
}