package com.example.challenge.presentation.screen.log_in.contract

sealed interface UiEvent {
    data object NavigateToConnections : UiEvent
}
