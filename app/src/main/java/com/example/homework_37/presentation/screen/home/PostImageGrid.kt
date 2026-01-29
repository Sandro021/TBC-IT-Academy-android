package com.example.homework_37.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.homework_37.presentation.ui.theme.Radius
import com.example.homework_37.presentation.ui.theme.Spacer

@Composable
fun PostImageGrid(images: List<String>) {

    when (images.size) {
        1 -> {
            AsyncImage(
                model = images[0],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(Radius.radius12)
            )
        }

        2 -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                horizontalArrangement = Arrangement.spacedBy(Spacer.space8)

            ) {
                AsyncImage(
                    model = images[0],
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(Radius.radius12),
                    contentScale = ContentScale.Crop
                )

                AsyncImage(
                    model = images[1],
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(Radius.radius12),
                    contentScale = ContentScale.Crop
                )
            }
        }

        3 -> {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                horizontalArrangement = Arrangement.spacedBy(Spacer.space8)
            ) {

                AsyncImage(
                    model = images[0],
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxHeight()
                        .clip(Radius.radius12),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(Spacer.space8)
                ) {
                    AsyncImage(
                        model = images[1],
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clip(Radius.radius12),
                        contentScale = ContentScale.Crop
                    )

                    AsyncImage(
                        model = images[2],
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clip(Radius.radius12),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}


@Composable
@Preview
private fun PostImageGridPreview() {
    PostImageGrid(
        listOf(
            "https://pohcdn.com/sites/default/files/styles/paragraph__live_banner__lb_image__1880bp/public/live_banner/batumi-1.jpg",
            "https://www.georgianholidays.com/storage/vc7se5NyjkI0mV4JhDdsAcfVZFPeQ68McYMUlmPw.jpeg",
        )
    )
}