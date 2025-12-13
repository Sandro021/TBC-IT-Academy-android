package com.example.gymtracker.presentation.screen.exercises

sealed interface ExercisesEffect {
    data class OpenGroup(val groupId: String, val title: String) : ExercisesEffect
    data object OpenNewExercise : ExercisesEffect
}