package com.example.exam_8.domain.model

data class ChatUser(
    val id: Int,
    val image: String?,
    val owner: String,
    val lastMessage: String,
    val lastActive: String,
    val unreadMessages: Int,
    val isTyping: Boolean,
    val lastMessageType: MessageType
)

enum class MessageType {
    TEXT, VOICE, FILE;

    companion object {
        fun fromApi(value: String?): MessageType {
            return when (value?.lowercase()?.trim()) {
                "text" -> TEXT
                "voice" -> VOICE
                "file" -> FILE
                else -> TEXT
            }
        }
    }
}


