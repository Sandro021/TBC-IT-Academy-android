package com.example.gymtracker.presentation.screen.home

import java.time.LocalDate

sealed interface CalendarIntent {
    data class DateSelected(val date: LocalDate) : CalendarIntent
    data class ExercisesPicked(val selected: List<Pair<String, String>>) : CalendarIntent

    data class DeleteExercise(val exerciseId: String) : CalendarIntent

    data object SaveClicked : CalendarIntent
    data class AddSetClicked(val exerciseId: String) : CalendarIntent
    data class SetWeightChanged(
        val exerciseId: String,
        val setNumber: Int,
        val value: String
    ) : CalendarIntent

    data class SetRepsChanged(
        val exerciseId: String,
        val setNumber: Int,
        val value: String
    ) : CalendarIntent
    object ClickLogout : CalendarIntent
    object NavigationHandled : CalendarIntent
}