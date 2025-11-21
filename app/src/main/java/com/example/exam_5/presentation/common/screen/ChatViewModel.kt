package com.example.exam_5.presentation.common.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam_5.data.dto.MessageType
import com.example.exam_5.data.dto.repository.ChatRepository
import com.example.exam_5.presentation.common.intent.ChatIntent
import com.example.exam_5.presentation.common.state.ChatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()


    fun onIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.LoadChats -> load()
            is ChatIntent.Search -> search(intent.query)
            is ChatIntent.Filter -> filter(intent.type)
        }
    }

    private fun load() {
        viewModelScope.launch {
            runCatching { repository.loadChats() }.onSuccess { chats ->
                println("LOADED: ${chats.size}")
                _state.update { it.copy(chats = chats, filteredChats = chats) }
            }
                .onFailure { e ->
                    println("ERROR: ${e.message}")
                    _state.update { it.copy(error = e.message) }
                }
        }
    }

    private fun search(query: String) {
        _state.update { it.copy(searchQuery = query) }
        val base = _state.value.chats

        val filtered = base.filter {
            it.owner.contains(query, ignoreCase = true)
        }

        _state.update { it.copy(filteredChats = filtered) }
    }

    private fun filter(type: MessageType?) {
        _state.update { it.copy(filterType = type) }

        val base = _state.value.chats

        val filtered = if (type == null) base
        else base.filter { it.lastMessageType == type.rawValue }

        _state.update { it.copy(filteredChats = filtered) }
    }
}