package com.example.homework_30.domain.repository

import com.example.homework_30.domain.common.Resource
import com.example.homework_30.domain.model.CategoryDomainModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun searchCategories(query: String): Flow<Resource<List<CategoryDomainModel>>>
}