package com.example.exam_5.presentation.common.intent

import com.example.exam_5.data.dto.MessageType

sealed class ChatIntent {
    data object LoadChats : ChatIntent()
    data class Search(val query: String) : ChatIntent()
    data class Filter(val type: MessageType?) : ChatIntent()
}