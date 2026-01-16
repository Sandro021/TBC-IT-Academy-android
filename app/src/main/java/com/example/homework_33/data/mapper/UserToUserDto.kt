package com.example.homework_33.data.mapper

import com.example.homework_33.data.model.UserDto
import com.example.homework_33.domain.model.User

fun UserDto.toDomain(): User = User(
    uid = uid,
    email = email
)