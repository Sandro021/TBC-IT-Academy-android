package com.example.homework_24.presentation.user

import com.example.homework_24.data.datastore.UserInfo

data class UserState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val users: List<UserInfo> = emptyList(),
    val errorMessage: String? = null
)