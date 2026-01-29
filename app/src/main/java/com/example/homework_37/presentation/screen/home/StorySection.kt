package com.example.homework_37.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.homework_37.domain.model.Story
import com.example.homework_37.presentation.ui.theme.AppColor
import com.example.homework_37.presentation.ui.theme.CustomTypography
import com.example.homework_37.presentation.ui.theme.Padding
import com.example.homework_37.presentation.ui.theme.Radius
import com.example.homework_37.presentation.ui.theme.Spacer

@Composable
fun StorySection(stories: List<Story>) {

    LazyRow(
        contentPadding = PaddingValues(horizontal = Padding.padding20),
        horizontalArrangement = Arrangement.spacedBy(Spacer.space12),
        modifier = Modifier.fillMaxWidth()
    ) {

        items(stories) { story ->

            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(200.dp)
                    .clip(Radius.radius16)

            ) {

                AsyncImage(
                    model = story.imageUrl,
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
                                    AppColor.ColorBlack.copy(alpha = 0.6f)
                                ),
                                startY = 300f
                            )
                        )
                )

                Text(
                    text = story.name,
                    style = CustomTypography.bodyMedium,
                    color = AppColor.ColorWhite,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(Padding.padding12)
                )
            }
        }
    }
}


@Composable
@Preview
private fun StorySectionPreview() {
    StorySection(
        stories = listOf(Story(1, "mount", "https://i.ibb.co/cKHT18R6/cover-photo-1-scaled.png"))
    )
}

