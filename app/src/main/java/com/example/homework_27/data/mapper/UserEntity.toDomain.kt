package com.example.homework_27.data.mapper

import com.example.homework_27.data.local.UserEntity
import com.example.homework_27.domain.User

fun UserEntity.toDomain(): User = User(
    id = id,
    name = name,
    email = email,
    profileImageUrl = profileImageUrl,
    activationStatus = activationStatus,
    lastActiveDescription = lastActiveDescription,
    lastActiveEpoch = lastActiveEpoch
)