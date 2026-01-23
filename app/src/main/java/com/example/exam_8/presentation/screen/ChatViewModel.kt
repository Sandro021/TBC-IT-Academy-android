package com.example.exam_8.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam_8.domain.model.ChatUser
import com.example.exam_8.domain.model.MessageType
import com.example.exam_8.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getUsers: GetUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    init {
        loadUsers()

    }

    private fun loadUsers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val users = getUsers()

            _state.update {
                it.copy(
                    users = users,
                    filtered = users,
                    isLoading = false
                )
            }
        }
    }

    fun onSearch(text: String) {
        _state.update {
            it.copy(
                search = text,
                filtered = applyFilters(
                    users = it.users,
                    search = text,
                    filter = it.filter
                )
            )
        }
    }

    fun onFilter(type: MessageType?) {
        _state.update {
            it.copy(
                filter = type,
                filtered = applyFilters(
                    users = it.users,
                    search = it.search,
                    filter = type
                )
            )
        }
    }

    private fun applyFilters(
        users: List<ChatUser>,
        search: String,
        filter: MessageType?
    ): List<ChatUser> {
        return users
            .filter {
                it.owner.contains(search, ignoreCase = true)
            }
            .filter {
                filter == null || it.lastMessageType == filter
            }
    }

}