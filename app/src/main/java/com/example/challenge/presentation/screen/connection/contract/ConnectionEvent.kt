package com.example.challenge.presentation.screen.connection.contract

sealed class ConnectionEvent {
    object FetchConnections : ConnectionEvent()
    object LogOut : ConnectionEvent()
    object ResetErrorMessage : ConnectionEvent()

}