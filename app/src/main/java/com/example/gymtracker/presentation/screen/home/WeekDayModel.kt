package com.example.gymtracker.presentation.screen.home

import java.time.LocalDate

data class WeekDayModel(
    val date: LocalDate,
    val dayName: String,
    val dayNumber: String,
    val isSelected: Boolean = false
)
