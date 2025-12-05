package com.example.homework_27.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id")
    val id: String,
    @SerialName("full_name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("profile_image_url")
    val profileImageUrl: String?,
    @SerialName("activation_status")
    val activationStatus: String,
    @SerialName("last_active_description")
    val lastActiveDescription: String,
    @SerialName("last_active_epoch")
    val lastActiveEpoch: Long
)