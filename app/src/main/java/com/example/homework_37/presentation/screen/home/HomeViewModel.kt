package com.example.homework_37.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_37.data.common.Resource
import com.example.homework_37.domain.usecase.GetFeedUseCase
import com.example.homework_37.domain.usecase.GetStoriesUseCase
import com.example.homework_37.presentation.screen.home.contract.HomeIntent
import com.example.homework_37.presentation.screen.home.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase,
    private val getStoriesUseCase: GetStoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        handleIntent(HomeIntent.LoadData)
    }
    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadData -> loadAllData()
        }
    }

    private fun loadAllData() {
        viewModelScope.launch {
            getStoriesUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isStoriesLoading = true) }

                    is Resource.Success -> _state.update {
                        it.copy(
                            isStoriesLoading = false,
                            stories = result.data
                        )
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            isStoriesLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            getFeedUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isFeedLoading = true) }

                    is Resource.Success -> _state.update {
                        it.copy(
                            isFeedLoading = false,
                            posts = result.data
                        )
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            isFeedLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
}