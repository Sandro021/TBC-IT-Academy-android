package com.example.gymtracker.presentation.screen.home

import java.time.LocalDate

data class CalendarModel(
    val date: LocalDate,
    val dayName: String,
    val dayNumber: String,
    val isSelected: Boolean = false
)
