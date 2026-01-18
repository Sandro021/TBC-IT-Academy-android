package com.example.homework_33.presentation.screen.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homework_33.presentation.screen.event.contract.EventsEvent


@Composable
fun EventScreen(
    viewModel: EventsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(EventsEvent.Load)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF223238))
            .systemBarsPadding()
    ) {
        Column(Modifier.fillMaxSize()) {

            CategoryRow(
                selected = state.selected,
                onSelect = { viewModel.onEvent(EventsEvent.SelectCategory(it)) }
            )

            Spacer(Modifier.height(14.dp))

            if (state.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                EventsGrid(
                    items = state.filtered,
                    favorites = state.favorites,
                    onToggleFavorite = { viewModel.onEvent(EventsEvent.ToggleFavorite(it)) }
                )
            }
        }
    }

}

@Composable
@Preview
fun WelcomePreview() {
    EventScreen()
}
