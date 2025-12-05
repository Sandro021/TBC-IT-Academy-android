package com.example.homework_27.presentation.model

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String?,
    val activationStatus: String,
    val lastActiveDescription: String,
    val lastActiveEpoch: Long,
    val saveError: String? = null
)