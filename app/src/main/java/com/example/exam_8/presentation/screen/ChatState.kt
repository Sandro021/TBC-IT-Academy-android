package com.example.exam_8.presentation.screen

import com.example.exam_8.domain.model.ChatUser
import com.example.exam_8.domain.model.MessageType

data class ChatState(
    val users: List<ChatUser> = emptyList(),
    val filtered: List<ChatUser> = emptyList(),
    val search: String = "",
    val filter: MessageType? = null,
    val isLoading: Boolean = false
)
