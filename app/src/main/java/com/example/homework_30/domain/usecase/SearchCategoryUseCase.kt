package com.example.homework_30.domain.usecase

import com.example.homework_30.domain.common.Resource
import com.example.homework_30.domain.model.CategoryDomainModel
import com.example.homework_30.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCategoriesUseCase@Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<CategoryDomainModel>>> {
        return repository.searchCategories(query)
    }
}