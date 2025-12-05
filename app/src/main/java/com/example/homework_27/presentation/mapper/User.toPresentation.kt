package com.example.homework_27.presentation.mapper

import com.example.homework_27.data.local.UserEntity
import com.example.homework_27.presentation.model.UserModel

fun UserEntity.toPresentation(saveError: String? = null): UserModel = UserModel(
    id = id,
    name = name,
    email = email,
    profileImageUrl = profileImageUrl,
    activationStatus = activationStatus,
    lastActiveDescription = lastActiveDescription,
    lastActiveEpoch = lastActiveEpoch,
    saveError = saveError
)