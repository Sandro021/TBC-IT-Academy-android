package com.example.exam_8.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatUserDto(
    val id: Int,
    val image: String?,
    val owner: String,
    val last_message: String,
    val last_active: String,
    val unread_messages: Int,
    val is_typing: Boolean,
    @SerialName("last_message_type")
    val laste_message_type: String = "text"
)