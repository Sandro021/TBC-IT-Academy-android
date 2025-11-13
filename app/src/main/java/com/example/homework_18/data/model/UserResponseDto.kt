package com.example.homework_18.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val id: Int? = null,
    val token: String? = null
)