package com.example.homework_30.presentation.screen.search


import androidx.lifecycle.viewModelScope
import com.example.homework_30.domain.common.Resource
import com.example.homework_30.domain.usecase.SearchCategoriesUseCase
import com.example.homework_30.presentation.common.BaseViewModel
import com.example.homework_30.presentation.mapper.toUi
import com.example.homework_30.presentation.model.CategoryUiModel
import com.example.homework_30.presentation.screen.search.contract.SearchEffect
import com.example.homework_30.presentation.screen.search.contract.SearchIntent
import com.example.homework_30.presentation.screen.search.contract.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCategoriesUseCase: SearchCategoriesUseCase
) : BaseViewModel<SearchState, SearchEffect, SearchIntent>(SearchState()) {

    private val queryFlow = MutableStateFlow("")
    private var searchJob: Job? = null

    private var fullTree: List<CategoryUiModel> = emptyList()

    init {
        observeQuery()
    }

    override fun onEvent(event: SearchIntent) {
        when (event) {
            is SearchIntent.OnSearchQueryChanged -> {
                queryFlow.value = event.query
            }

            is SearchIntent.OnItemClicked -> toggleExpansion(event.item)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeQuery() {
        viewModelScope.launch {
            queryFlow
                .debounce(500L)
                .distinctUntilChanged()
                .collectLatest { query ->
                    fetchCategories(query)
                }
        }
    }

    private fun fetchCategories(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchCategoriesUseCase(query).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        updateState { copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        fullTree = result.data.map { it.toUi() }


                        val displayList = rebuildDisplayList(fullTree, query)

                        updateState { copy(isLoading = false, categories = displayList) }
                    }

                    is Resource.Error -> {
                        updateState { copy(isLoading = false) }
                        sendEffect(SearchEffect.ShowError(result.message))
                    }
                }
            }
        }
    }

    private fun toggleExpansion(item: CategoryUiModel) {

        findAndToggle(fullTree, item.id)


        val currentQuery = queryFlow.value
        val newDisplayList = rebuildDisplayList(fullTree, currentQuery)


        updateState { copy(categories = newDisplayList) }
    }

    private fun findAndToggle(nodes: List<CategoryUiModel>, id: String): Boolean {
        for (node in nodes) {
            if (node.id == id) {
                node.isExpanded = !node.isExpanded
                return true
            }

            if (findAndToggle(node.children, id)) return true
        }
        return false
    }

    private fun rebuildDisplayList(
        nodes: List<CategoryUiModel>,
        query: String
    ): List<CategoryUiModel> {
        val result = mutableListOf<CategoryUiModel>()

        if (query.isNotBlank()) {

            flattenForSearch(nodes, query, result)
        } else {

            flattenForTree(nodes, result)
        }
        return result
    }

    private fun flattenForTree(nodes: List<CategoryUiModel>, result: MutableList<CategoryUiModel>) {
        for (node in nodes) {
            result.add(node)
            if (node.isExpanded) {
                flattenForTree(node.children, result)
            }
        }
    }

    private fun flattenForSearch(
        nodes: List<CategoryUiModel>,
        query: String,
        result: MutableList<CategoryUiModel>
    ) {
        for (node in nodes) {

            if (node.name.contains(query, ignoreCase = true)) {
                result.add(node)
            }

            flattenForSearch(node.children, query, result)
        }
    }
}