package com.example.gymtracker.presentation.screen.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class TodayViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(TodayState())
    val state = _state.asStateFlow()

    init {
        rebuildWeek(LocalDate.now())
    }

    fun processIntent(intent: TodayIntent) {
        when (intent) {
            is TodayIntent.DateSelected -> rebuildWeek(intent.date)
        }
    }

    private fun rebuildWeek(selected: LocalDate) {
        val start = selected.with(DayOfWeek.MONDAY)
        val days = (0..6).map { i ->
            val date = start.plusDays(i.toLong())
            WeekDayModel(
                date = date,
                dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                dayNumber = date.dayOfMonth.toString(),
                isSelected = date == selected
            )
        }
        _state.update { it.copy(selectedDate = selected, week = days) }
    }
}
