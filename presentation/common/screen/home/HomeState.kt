package com.example.homework_20.presentation.common.screen.home

import com.example.homework_20.domain.model.User

data class HomeState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val errorMessage: String? = null
)
