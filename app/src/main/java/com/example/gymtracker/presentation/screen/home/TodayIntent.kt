package com.example.gymtracker.presentation.screen.home

import java.time.LocalDate

sealed interface TodayIntent {
    data class DateSelected(val date: LocalDate) : TodayIntent
    data class ExercisesPicked(val selected: List<Pair<String, String>>) : TodayIntent

    data class DeleteExercise(val exerciseId: String) : TodayIntent

    data object SaveClicked : TodayIntent
    data class AddSetClicked(val exerciseId: String) : TodayIntent
    data class SetWeightChanged(
        val exerciseId: String,
        val setNumber: Int,
        val value: String
    ) : TodayIntent

    data class SetRepsChanged(
        val exerciseId: String,
        val setNumber: Int,
        val value: String
    ) : TodayIntent
}