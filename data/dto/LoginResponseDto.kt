package com.example.homework_20.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String?
)