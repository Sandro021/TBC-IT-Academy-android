package com.example.gymtracker.presentation.screen.home

import java.time.LocalDate

sealed interface TodayIntent {
    data class DateSelected(val date: LocalDate) : TodayIntent
}