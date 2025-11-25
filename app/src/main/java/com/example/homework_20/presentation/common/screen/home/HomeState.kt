package com.example.homework_20.presentation.common.screen.home

import com.example.homework_20.data.dto.UserDto

data class HomeState(
    val isLoading: Boolean = false,
    val users: List<UserDto> = emptyList(),
    val errorMessage: String? = null
)
