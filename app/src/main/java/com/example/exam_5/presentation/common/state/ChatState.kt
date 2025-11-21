package com.example.exam_5.presentation.common.state

import com.example.exam_5.data.dto.ChatItemDto
import com.example.exam_5.data.dto.MessageType

data class ChatState(
    val chats: List<ChatItemDto> = emptyList(),
    val filteredChats: List<ChatItemDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val filterType: MessageType? = null
)