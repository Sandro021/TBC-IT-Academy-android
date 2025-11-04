package com.example.homework_16.viewModel

import androidx.lifecycle.ViewModel
import com.example.homework_16.message_info.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())

    val messages = _messages.asStateFlow()

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        _messages.update { currentList ->
            listOf(Message(text = text)) + currentList
        }
    }
}