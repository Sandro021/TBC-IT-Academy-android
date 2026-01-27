package com.example.homework_36.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_36.domain.usecase.GetToursUseCase
import com.example.homework_36.presentation.screen.tour.contract.TourIntent
import com.example.homework_36.presentation.screen.tour.contract.TourState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TourViewModel @Inject constructor(
    private val getToursUseCase: GetToursUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TourState())
    val state = _state.asStateFlow()


    fun onIntent(intent: TourIntent) {
        when (intent) {
            TourIntent.LoadTours -> loadTours()
        }
    }

    private fun loadTours() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching { getToursUseCase() }.onSuccess {
                _state.update { s -> s.copy(isLoading = false, tours = it) }
            }.onFailure {
                _state.update { s -> s.copy(isLoading = false, error = it.message) }
            }
        }
    }
}