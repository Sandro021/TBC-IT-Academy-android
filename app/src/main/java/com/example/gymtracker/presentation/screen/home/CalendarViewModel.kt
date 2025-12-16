package com.example.gymtracker.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtracker.domain.model.WorkoutItem
import com.example.gymtracker.domain.model.WorkoutSet
import com.example.gymtracker.domain.usecase.DeleteWorkoutItemUseCase
import com.example.gymtracker.domain.usecase.LogoutUseCase
import com.example.gymtracker.domain.usecase.ObserveWorkoutUseCase
import com.example.gymtracker.domain.usecase.SaveWorkoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val observeWorkoutUseCase: ObserveWorkoutUseCase,
    private val saveWorkoutUseCase: SaveWorkoutUseCase,
    private val deleteWorkoutItemUseCase: DeleteWorkoutItemUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CalendarState())
    val state = _state.asStateFlow()

    private val startDate = LocalDate.now().minusYears(2)
    private val endDate = LocalDate.now().plusYears(1)

    private var observeJob: kotlinx.coroutines.Job? = null

    init {
        val today = LocalDate.now()
        buildCalendar(today)
        observeWorkoutForDate(today)
    }

    fun processIntent(intent: CalendarIntent) {
        when (intent) {
            is CalendarIntent.DateSelected -> onDateSelected(intent.date)
            is CalendarIntent.ExercisesPicked -> onExercisesPicked(intent.selected)
            is CalendarIntent.SetRepsChanged -> onSetRepsChanged(
                intent.exerciseId,
                intent.setNumber,
                intent.value
            )

            is CalendarIntent.SetWeightChanged -> onSetWeightChanged(
                intent.exerciseId,
                intent.setNumber,
                intent.value
            )

            is CalendarIntent.SaveClicked -> onSaveClicked()
            is CalendarIntent.DeleteExercise -> deleteExercise(intent.exerciseId)
            is CalendarIntent.AddSetClicked -> onAddSetClicked(intent.exerciseId)
            is CalendarIntent.ClickLogout -> logOut()
            is CalendarIntent.NavigationHandled -> _state.update { it.copy(navigateToLogin = false) }
        }
    }


    private fun onDateSelected(date: LocalDate) {
        buildCalendar(date)
        observeWorkoutForDate(date)
    }

    private fun logOut() {
        viewModelScope.launch {
            logoutUseCase()
            _state.update { it.copy(navigateToLogin = true) }
        }
    }

    private fun onSetWeightChanged(exerciseId: String, setNumber: Int, value: String) {
        _state.update { s ->
            s.copy(items = s.items.map { item ->
                if (item.exerciseId != exerciseId) return@map item
                item.copy(sets = item.sets.map { set ->
                    if (set.number == setNumber) set.copy(weight = value) else set
                })
            })
        }
    }

    private fun onSetRepsChanged(exerciseId: String, setNumber: Int, value: String) {
        _state.update { s ->
            s.copy(items = s.items.map { item ->
                if (item.exerciseId != exerciseId) return@map item
                item.copy(sets = item.sets.map { set ->
                    if (set.number == setNumber) set.copy(reps = value) else set
                })
            })
        }
    }

    private fun deleteExercise(exerciseId: String) {
        val key = dateKey(_state.value.selectedDate)
        viewModelScope.launch {
            deleteWorkoutItemUseCase(key, exerciseId)
        }

    }

    private fun observeWorkoutForDate(date: LocalDate) {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            val key = dateKey(date)
            observeWorkoutUseCase(key).collect { domainItems ->
                val uiItems = domainItems.map { item ->
                    WorkoutItemModel(
                        exerciseId = item.exerciseId,
                        name = item.name,
                        sets = item.sets.map { s ->
                            WorkoutSetModel(
                                exerciseId = item.exerciseId,
                                number = s.number,
                                weight = s.weight.toString(),
                                reps = s.reps.toString()
                            )
                        }.ifEmpty {
                            listOf(
                                WorkoutSetModel(
                                    exerciseId = item.exerciseId,
                                    number = 1
                                )
                            )
                        }
                    )
                }
                _state.update { it.copy(items = uiItems, errorMessage = null) }
            }
        }
    }

    private fun onExercisesPicked(selected: List<Pair<String, String>>) {
        _state.update { s ->
            val existing = s.items.associateBy { it.exerciseId }.toMutableMap()
            for ((id, name) in selected) {
                if (!existing.containsKey(id)) {
                    existing[id] = WorkoutItemModel(
                        exerciseId = id,
                        name = name,
                        weight = "",
                        reps = ""
                    )
                }
            }
            s.copy(items = existing.values.sortedBy { it.name })
        }
    }


    private fun onAddSetClicked(exerciseId: String) {
        _state.update { s ->
            s.copy(
                items = s.items.map { item ->
                    if (item.exerciseId == exerciseId) {

                        val maxNumber = item.sets.maxOfOrNull { it.number } ?: 0
                        val nextNumber = maxNumber + 1

                        item.copy(
                            sets = item.sets + WorkoutSetModel(
                                exerciseId = exerciseId,
                                number = nextNumber,
                                weight = "",
                                reps = ""
                            )
                        )
                    } else item
                }
            )
        }
    }

    private fun onSaveClicked() {
        val key = dateKey(_state.value.selectedDate)
        val items = _state.value.items.map { ui ->
            WorkoutItem(
                exerciseId = ui.exerciseId,
                name = ui.name,
                sets = ui.sets.map { s ->
                    WorkoutSet(
                        number = s.number,
                        weight = s.weight.toDoubleOrNull() ?: 0.0,
                        reps = s.reps.toIntOrNull() ?: 0
                    )
                }
            )
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, errorMessage = null) }

            saveWorkoutUseCase(key, items)
                .onSuccess {
                    _state.update { it.copy(isSaving = false) }
                }
                .onFailure { e ->
                    _state.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = e.message ?: "Save failed"
                        )
                    }
                }
        }

    }

    private fun buildCalendar(selected: LocalDate) {
        val today = LocalDate.now()

        val title = if (selected == today) {
            "Today"
        } else {
            val formatter = java.time.format.DateTimeFormatter
                .ofPattern("dd MMM yyyy", Locale.ENGLISH)
            selected.format(formatter)
        }

        val days = generateSequence(startDate) { it.plusDays(1) }
            .takeWhile { !it.isAfter(endDate) }
            .map { date ->
                CalendarModel(
                    date = date,
                    dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                    dayNumber = date.dayOfMonth.toString(),
                    isSelected = date == selected
                )
            }
            .toList()

        _state.value = _state.value.copy(
            selectedDate = selected,
            calendarDays = days,
            headerTitle = title,
            todayIndex = java.time.temporal.ChronoUnit.DAYS.between(startDate, today).toInt()
        )
    }

    private fun dateKey(date: LocalDate) = date.toString()
}

