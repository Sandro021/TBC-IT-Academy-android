package com.example.gymtracker.presentation.screen.home

import java.time.LocalDate

data class TodayState(
    val selectedDate: LocalDate = LocalDate.now(),
    val week: List<WeekDayModel> = emptyList()
)