package com.example.gymtracker.presentation.screen.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtracker.domain.usecase.ObserveExerciseGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val observeExerciseGroupUseCase: ObserveExerciseGroupUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ExercisesState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ExercisesEffect>()
    val effect = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            observeExerciseGroupUseCase().collect { groups ->
                _state.update { s ->
                    val q = s.query.trim()
                    val filtered = if (q.isEmpty()) groups else {
                        groups.filter { it.title.contains(q, ignoreCase = true) }
                    }
                    s.copy(
                        isLoading = false,
                        groups = groups,
                        filteredGroups = filtered,
                        errorMessage = null
                    )
                }
            }
        }
    }

    fun processIntent(intent: ExercisesIntent) {
        when (intent) {
            is ExercisesIntent.SearchChanged -> {
                _state.update { s ->
                    val q = intent.value
                    val filtered = if (q.isBlank()) s.groups else {
                        s.groups.filter { it.title.contains(q, ignoreCase = true) }
                    }
                    s.copy(query = q, filteredGroups = filtered)
                }
            }

            is ExercisesIntent.GroupClicked -> {
                viewModelScope.launch {
                    _effect.emit(ExercisesEffect.OpenGroup(intent.groupId, intent.title))
                }
            }

            ExercisesIntent.NewExerciseClicked -> {
                viewModelScope.launch { _effect.emit(ExercisesEffect.OpenNewExercise) }
            }
        }
    }

}