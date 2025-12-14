package com.example.gymtracker.presentation.screen.exercises

sealed interface ExercisesIntent {
    data class SearchChanged(val value: String) : ExercisesIntent
    data class GroupClicked(val groupId: String, val title: String) : ExercisesIntent
    data object NewExerciseClicked : ExercisesIntent
    data class SaveNewExercise(val name: String, val groupId: String, val groupTitle: String) :
        ExercisesIntent
}