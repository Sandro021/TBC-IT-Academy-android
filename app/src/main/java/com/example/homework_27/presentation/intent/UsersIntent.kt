package com.example.homework_27.presentation.intent

sealed interface UsersIntent {
    data object InitialLoad : UsersIntent
    data object PullToRefresh : UsersIntent
}