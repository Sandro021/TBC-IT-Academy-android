package com.example.homework_27.presentation.state

import com.example.homework_27.presentation.model.UserModel

data class UsersState(
    val isLoading: Boolean = false,
    val isOffline: Boolean = false,
    val users: List<UserModel> = emptyList(),
    val globalError: String? = null
)