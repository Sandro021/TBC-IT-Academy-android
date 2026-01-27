package com.example.homework_36.presentation.screen.tour

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.homework_36.domain.model.Tour
import com.example.homework_36.presentation.ui.theme.AppColor
import com.example.homework_36.presentation.ui.theme.AppTypography
import com.example.homework_36.presentation.ui.theme.FontSize
import com.example.homework_36.presentation.ui.theme.Height
import com.example.homework_36.presentation.ui.theme.Padding
import com.example.homework_36.presentation.ui.theme.Radius
import com.example.homework_36.presentation.ui.theme.Size
import com.example.homework_36.presentation.ui.theme.Space
import com.example.homework_36.presentation.ui.theme.Width

@Composable
fun TourCard(
    tour: Tour,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(Height.height520)
            .clip(Radius.radius28)
    ) {


        AsyncImage(
            model = tour.photo,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            AppColor.ColorTransparent,
                            AppColor.ColorBlack.copy(alpha = 0.8f)
                        )
                    )
                )
        )


        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(Padding.padding16),
            horizontalArrangement = Arrangement.spacedBy(Space.space12)
        ) {
            InfoChip(Icons.Default.LocationOn, tour.location)
            InfoChip(Icons.Default.PlayArrow, tour.number)
        }


        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Padding.padding20)
        ) {
            Text(
                text = tour.title,
                style = AppTypography.titleLarge,
                color = AppColor.ColorWhite
            )

            Spacer(modifier = Modifier.height(Height.height8))

            Text(
                text = "$${tour.price}",
                style = AppTypography.bodyLarge,
                color = AppColor.ColorWhite
            )

            Spacer(modifier = Modifier.height(Height.height8))

            StarRating(tour.stars)
        }
    }
}


@Composable
@Preview
private fun TourCardPreview() {
    TourCard(
        Tour(
            "ragaca title",
            "Barcelona",
            "2500",
            "https://picsum.photos/seed/tour1/800/1200",
            120,
            5
        )
    )
}

@Composable
fun StarRating(stars: Int) {
    Row {
        repeat(5) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (it < stars) AppColor.ColorOrange else AppColor.ColorGray
            )
        }
    }
}

@Composable
fun InfoChip(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .background(
                AppColor.ColorBlack.copy(alpha = 0.5f),
                Radius.radius50
            )
            .padding(horizontal = Padding.padding8, vertical = Padding.padding4),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(icon, null, tint = Color.White, modifier = Modifier.size(Size.size14))
        Spacer(Modifier.width(Width.width4))
        Text(text, color = AppColor.ColorWhite, fontSize = FontSize.fontSize12)
    }
}

