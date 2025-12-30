package com.example.homework_30.presentation.screen.search.contract

import com.example.homework_30.presentation.model.CategoryUiModel

sealed class SearchIntent {
    data class OnSearchQueryChanged(val query: String) : SearchIntent()
    data class OnItemClicked(val item: CategoryUiModel) : SearchIntent()
}