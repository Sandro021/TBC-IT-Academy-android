package com.example.homework_20.data.mapper

import com.example.homework_20.data.dto.UserDto
import com.example.homework_20.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}