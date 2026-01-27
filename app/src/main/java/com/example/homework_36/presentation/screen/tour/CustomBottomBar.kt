package com.example.homework_36.presentation.screen.tour

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.homework_36.R
import com.example.homework_36.presentation.ui.theme.AppColor
import com.example.homework_36.presentation.ui.theme.Height
import com.example.homework_36.presentation.ui.theme.Padding
import com.example.homework_36.presentation.ui.theme.Size

@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Height.height80)
            .background(
                color = AppColor.BottomBarBackground,
                shape = RoundedCornerShape(topStart = Size.size32, topEnd = Size.size32)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.padding40),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.favorites),
                tint = AppColor.ColorGray,
                modifier = Modifier.size(Size.size28)
            )


            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = AppColor.MintGreenColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.home),
                    tint = Color.White,
                    modifier = Modifier.size(Size.size28)
                )
            }


            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = stringResource(R.string.chat),
                tint = AppColor.ColorGray,
                modifier = Modifier.size(Size.size28)
            )
        }
    }
}

@Composable
@Preview
private fun CustomBottomBarPreview() {
    CustomBottomBar()
}