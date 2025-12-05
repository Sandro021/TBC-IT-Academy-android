package com.example.exam_6.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.exam_6.domain.usecase.GetWorksSpaceUseCase
import com.example.exam_6.presentation.intent.HomeIntent
import com.example.exam_6.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWorksSpaceUseCase: GetWorksSpaceUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadWorkSpaces -> loadWorkSpaces()
        }
    }

    private fun loadWorkSpaces() {
        val flow = getWorksSpaceUseCase().cachedIn(viewModelScope)
        _state.update { it.copy(workspaces = flow, isLoading = false, error = null) }
    }
}