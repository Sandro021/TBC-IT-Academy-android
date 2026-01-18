package com.example.homework_33.presentation.screen.event.contract

sealed interface EventsEffect {
    data class ShowMessage(val text: String) : EventsEffect
}