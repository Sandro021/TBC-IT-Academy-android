package com.example.homework_20.data.mapper

import com.example.homework_20.data.dto.LoginResponseDto
import com.example.homework_20.domain.model.LoggedInUser

fun LoginResponseDto.toDomain(): LoggedInUser {
    return LoggedInUser(
        token = token ?: ""
    )
}