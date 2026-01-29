package com.example.homework_37.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.homework_37.R
import com.example.homework_37.domain.model.Post
import com.example.homework_37.presentation.ui.theme.AppColor
import com.example.homework_37.presentation.ui.theme.CustomTypography
import com.example.homework_37.presentation.ui.theme.Padding
import com.example.homework_37.presentation.ui.theme.Radius
import com.example.homework_37.presentation.ui.theme.Spacer

@Composable
fun FeedPostCard(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.padding20),

        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),

        shape = Radius.radius24

    ) {
        Column(modifier = Modifier.padding(Padding.padding16)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                AsyncImage(
                    model = post.avatarUrl ?: "https://ui-avatars.com/api/?name=${post.authorName}",
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(Spacer.space12))

                Column {

                    Text(post.authorName, style = CustomTypography.titleMedium)

                    Text(
                        post.date,
                        style = CustomTypography.bodySmall,
                        color = AppColor.DarkGrey
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacer.space12))


            post.content?.let {
                Text(text = it, style = CustomTypography.bodyMedium)

                Spacer(modifier = Modifier.height(Spacer.space12))
            }


            if (post.imageUrls.isNotEmpty()) {

                PostImageGrid(images = post.imageUrls)
            }


            Spacer(modifier = Modifier.height(Spacer.space16))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()

            ) {

                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = AppColor.TextSecondary
                )

                Text(
                    "${post.comments} Comments",
                    modifier = Modifier.padding(start = Padding.padding4),
                    color = AppColor.TextSecondary
                )

                Spacer(modifier = Modifier.width(Spacer.space16))

                Icon(Icons.Outlined.FavoriteBorder, contentDescription = null, tint = AppColor.TextSecondary)

                Text(
                    "${post.likes} Likes",
                    modifier = Modifier.padding(start = Padding.padding4),
                    color = AppColor.TextSecondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(Icons.Outlined.Share, contentDescription = null, tint = AppColor.TextSecondary)

            }
            Spacer(modifier = Modifier.height(Spacer.space16))

            HorizontalDivider(thickness = 0.5.dp, color = AppColor.TextSecondary.copy(alpha = 0.3f))

            Spacer(modifier = Modifier.height(Spacer.space16))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(

                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(AppColor.AccentGreen),
                    contentAlignment = Alignment.Center
                ) {

                    Text(stringResource(R.string.me), fontSize = 10.sp, color = AppColor.ColorWhite)

                }

                Spacer(modifier = Modifier.width(Spacer.space8))

                BasicTextField(
                    value = stringResource(R.string.write_comment),
                    onValueChange = {},
                    textStyle = androidx.compose.ui.text.TextStyle(color = AppColor.TextSecondary),
                    modifier = Modifier.weight(1f)
                )

                Icon(Icons.Outlined.Favorite, contentDescription = null, tint = AppColor.TextSecondary)
            }
        }
    }
}

@Preview
@Composable
private fun FeedPostCardPreview() {
    FeedPostCard(Post(1,"Sandro","https://randomuser.me/api/portraits/women/45.jpg","","",listOf("https://eurasia.travel/wp-content/uploads/2025/04/1.-Borjomi-town.jpg"),3,5))
}