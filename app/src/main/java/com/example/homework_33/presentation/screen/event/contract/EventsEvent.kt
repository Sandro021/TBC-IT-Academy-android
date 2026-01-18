package com.example.homework_33.presentation.screen.event.contract

import com.example.homework_33.domain.model.EventCategory

sealed interface EventsEvent {
    data object Load : EventsEvent
    data class SelectCategory(val category: EventCategory) : EventsEvent
    data class ToggleFavorite(val id: String) : EventsEvent
}