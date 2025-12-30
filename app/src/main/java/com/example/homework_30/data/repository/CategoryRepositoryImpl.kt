package com.example.homework_30.data.repository

import com.example.homework_30.data.common.safeApiCall
import com.example.homework_30.data.mapper.toDomain
import com.example.homework_30.data.remote.ApiService
import com.example.homework_30.domain.common.Resource
import com.example.homework_30.domain.model.CategoryDomainModel
import com.example.homework_30.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CategoryRepository {
    override fun searchCategories(query: String): Flow<Resource<List<CategoryDomainModel>>> = flow {
        emit(Resource.Loading)

        val result = safeApiCall { apiService.getCategories(query) }

        when (result) {
            is Resource.Success -> {
                val tree = result.data.map { it.toDomain() }

                emit(Resource.Success(tree))
            }

            is Resource.Error -> emit(Resource.Error(result.message))
            is Resource.Loading -> emit(Resource.Loading)
        }
    }


}