package com.example.homework_30.presentation.screen.search.contract

import com.example.homework_30.presentation.model.CategoryUiModel

data class SearchState(
    val isLoading: Boolean = false,
    val categories: List<CategoryUiModel> = emptyList(),
    val errorMessage: String? = null
)
