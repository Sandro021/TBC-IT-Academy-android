package com.example.gymtracker.presentation.screen.exercises

import com.example.gymtracker.domain.model.ExerciseGroup

data class ExercisesState(
    val isLoading: Boolean = true,
    val query: String = "",
    val groups: List<ExerciseGroup> = emptyList(),
    val filteredGroups: List<ExerciseGroup> = emptyList(),
    val errorMessage: String? = null
)
