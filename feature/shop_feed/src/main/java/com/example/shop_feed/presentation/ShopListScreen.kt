package com.example.shop_feed.presentation


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.shop_feed.presentation.model.ShopUiModel
import com.example.ui.theme.MallTheme
import com.example.ui.theme.Padding
import com.example.ui.theme.Radius
import com.example.ui.theme.Spacing


@Composable
fun ShopListScreen(
    viewModel: ShopListViewModel = hiltViewModel(),
    onShopClick: (String) -> Unit
) {

    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        if (state.error != null) {
            Text(
                text = "Error: ${state.error}",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(Padding.padding16),
            horizontalArrangement = Arrangement.spacedBy(Spacing.space16),
            verticalArrangement = Arrangement.spacedBy(Spacing.space16)
        ) {

            items(state.shops) { shop ->
                ShopItemCard(shop = shop, onClick = { onShopClick(shop.id) })
            }
        }
    }
}


@Composable
fun ShopItemCard(shop: ShopUiModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() },

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = Radius.radius12

    ) {
        Column {
            AsyncImage(
                model = shop.imageUrl,
                contentDescription = shop.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Column(modifier = Modifier.padding(Padding.padding12)) {
                Text(
                    text = shop.name,
                    style = MallTheme.typography.productTitle,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = shop.location,
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.Gray,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
            }
        }
    }
}