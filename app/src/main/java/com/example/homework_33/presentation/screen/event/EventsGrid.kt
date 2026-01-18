package com.example.homework_33.presentation.screen.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homework_33.domain.model.Event

@Composable
 fun EventsGrid(
    items: List<Event>,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = items.size,
            key = { items[it].id }
        ) { index ->
            val item = items[index]
            EventCard(
                event = item,
                isFavorite = favorites.contains(item.id),
                onFavoriteClick = { onToggleFavorite(item.id) }
            )
        }
    }
}


@Composable
@Preview
private fun EventsGridPreview() {
    EventsGrid(listOf(), setOf()) {}
}