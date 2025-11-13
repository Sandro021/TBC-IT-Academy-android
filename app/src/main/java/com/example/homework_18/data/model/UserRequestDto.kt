package com.example.homework_18.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRequestDto(
    val email: String,
    val password: String
)