package com.example.exam_8.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    Box() {
        Column {
            ChatTopBar(
                search = state.search,
                onSearch = viewModel::onSearch,
                onFilter = viewModel::onFilter
            )

            LazyColumn {
                items(state.filtered) { user ->
                    ChatItem(user)
                }
            }
        }
    }

}