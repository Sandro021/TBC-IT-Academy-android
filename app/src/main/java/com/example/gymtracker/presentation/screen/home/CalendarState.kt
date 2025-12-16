package com.example.gymtracker.presentation.screen.home

import java.time.LocalDate

data class CalendarState(
    val selectedDate: LocalDate = LocalDate.now(),
    val calendarDays: List<CalendarModel> = emptyList(),
    val headerTitle: String = "Today",
    val todayIndex: Int = 0,
    val navigateToLogin: Boolean = false,

    val items: List<WorkoutItemModel> = emptyList(),
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)