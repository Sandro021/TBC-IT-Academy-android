package com.example.homework_33.presentation.screen.event.contract

import com.example.homework_33.domain.model.Event
import com.example.homework_33.domain.model.EventCategory

data class EventsState(
    val isLoading: Boolean = false,
    val selected: EventCategory = EventCategory.ALL,
    val allEvents: List<Event> = emptyList(),
    val favorites: Set<String> = emptySet(),
    val error: String? = null,
) {
    val filtered: List<Event> =
        if (selected == EventCategory.ALL) allEvents
        else allEvents.filter { it.category == selected }
}