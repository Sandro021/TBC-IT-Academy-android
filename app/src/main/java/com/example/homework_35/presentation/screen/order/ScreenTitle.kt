package com.example.homework_35.presentation.screen.order

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework_35.R
import com.example.homework_35.presentation.ui.theme.FontSize
import com.example.homework_35.presentation.ui.theme.IconSize
import com.example.homework_35.presentation.ui.theme.Padding

@Composable
fun ScreenTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding.padding20),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Icon(
            painter = painterResource(R.drawable.menu),
            contentDescription = stringResource(R.string.menu_icon),
            modifier = IconSize.iconSize25
        )

        Text(
            text = stringResource(R.string.my_orders),
            fontSize = FontSize.fontSize25,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Icon(
            painter = painterResource(R.drawable.bell),
            contentDescription = stringResource(R.string.bell_icon),
            modifier = IconSize.iconSize25
        )
    }

}


@Composable
@Preview
private fun ScreenTitlePreview() {
    ScreenTitle()
}