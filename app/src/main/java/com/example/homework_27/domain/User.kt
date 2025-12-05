package com.example.homework_27.domain

data class User(
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String?,
    val activationStatus: String,
    val lastActiveDescription: String,
    val lastActiveEpoch: Long
)
