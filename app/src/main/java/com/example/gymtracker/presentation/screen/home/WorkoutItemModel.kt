package com.example.gymtracker.presentation.screen.home



data class WorkoutItemModel(
    val exerciseId: String,
    val name: String,
    val weight: String = "",
    val reps: String = "",
    val sets: List<WorkoutSetModel> = listOf(WorkoutSetModel(number = 1))
)
