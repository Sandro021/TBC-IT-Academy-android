package com.example.homework_33.presentation.screen.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_33.domain.usecase.event.GetEventsUseCase
import com.example.homework_33.presentation.screen.event.contract.EventsEffect
import com.example.homework_33.presentation.screen.event.contract.EventsEvent
import com.example.homework_33.presentation.screen.event.contract.EventsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EventsState(isLoading = true))
    val state = _state.asStateFlow()

    private val _effect = Channel<EventsEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()


    fun onEvent(event: EventsEvent) {
        when (event) {
            EventsEvent.Load -> load()
            is EventsEvent.SelectCategory -> _state.update { it.copy(selected = event.category) }
            is EventsEvent.ToggleFavorite -> _state.update {
                val newSet = it.favorites.toMutableSet()
                if (!newSet.add(event.id)) newSet.remove(event.id)
                it.copy(favorites = newSet)
            }
        }
    }

    private fun load() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }
        runCatching { getEventsUseCase() }
            .onSuccess { list ->
                _state.update { it.copy(isLoading = false, allEvents = list) }
            }
            .onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.message ?: "Error") }
                _effect.send(EventsEffect.ShowMessage("Failed to load"))
            }
    }


}