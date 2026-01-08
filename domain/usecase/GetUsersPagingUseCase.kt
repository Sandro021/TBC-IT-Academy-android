package com.example.homework_20.domain.usecase

import androidx.paging.PagingData
import com.example.homework_20.data.repository.UsersPagingRepositoryImpl
import com.example.homework_20.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersPagingUseCase @Inject constructor(
    private val repository: UsersPagingRepositoryImpl
) {
    operator fun invoke(): Flow<PagingData<User>> {
        return repository.getUsersPaging()
    }
}