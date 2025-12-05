package com.example.homework_27.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String?,
    val activationStatus: String,
    val lastActiveDescription: String,
    val lastActiveEpoch: Long,
    val saveError: String? = null
)