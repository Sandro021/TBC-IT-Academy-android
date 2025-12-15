package com.example.gymtracker.presentation.screen.exercise_choose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtracker.domain.usecase.CreateExerciseUseCase
import com.example.gymtracker.domain.usecase.ObserveExercisesByGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickExercisesViewModel @Inject constructor(
    private val observeExercisesByGroup: ObserveExercisesByGroupUseCase,
    private val createExercise: CreateExerciseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PickExercisesState())
    val state = _state.asStateFlow()

    fun start(groupId: String, groupTitle: String) {
        _state.update { it.copy(groupId = groupId, groupTitle = groupTitle) }

        viewModelScope.launch {
            observeExercisesByGroup(groupId).collect { list ->
                _state.update { s ->
                    val ui = list.map { ex ->
                        PickExerciseModel(ex.id, ex.name, s.selectedIds.contains(ex.id))
                    }
                    s.copy(exercises = list, ui = ui)
                }
            }
        }
    }

    fun toggle(exerciseId: String) {
        _state.update { s ->
            val newSet = s.selectedIds.toMutableSet()
            if (!newSet.add(exerciseId)) newSet.remove(exerciseId)

            val ui = s.exercises.map { ex ->
                PickExerciseModel(ex.id, ex.name, newSet.contains(ex.id))
            }
            s.copy(selectedIds = newSet, ui = ui)
        }
    }

    fun addExercise(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return

        val s = _state.value
        viewModelScope.launch {
            createExercise(trimmed, s.groupId, s.groupTitle)
        }
    }
}