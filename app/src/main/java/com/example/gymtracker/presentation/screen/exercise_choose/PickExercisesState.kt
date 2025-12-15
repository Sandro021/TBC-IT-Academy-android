package com.example.gymtracker.presentation.screen.exercise_choose

import com.example.gymtracker.domain.model.Exercise

data class PickExercisesState(
    val groupId: String = "",
    val groupTitle: String = "",
    val exercises: List<Exercise> = emptyList(),
    val selectedIds: Set<String> = emptySet(),
    val ui: List<PickExerciseModel> = emptyList()
)
