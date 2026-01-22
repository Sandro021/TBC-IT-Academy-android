package com.example.homework_35.presentation.screen.order

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework_35.domain.model.OrderStatus
import com.example.homework_35.presentation.ui.theme.Color
import com.example.homework_35.presentation.ui.theme.Height
import com.example.homework_35.presentation.ui.theme.Padding
import com.example.homework_35.presentation.ui.theme.Radius
import com.example.homework_35.presentation.ui.theme.SpaceBy
import com.example.homework_35.presentation.ui.theme.Typography


@Composable
fun Status(
    selected: OrderStatus,
    onSelected: (OrderStatus) -> Unit
) {
    val shape = Radius.radius24

    Row(
        modifier = Modifier
            .padding(horizontal = Padding.padding16, vertical = Padding.padding10)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(SpaceBy.spaceBy10)

    ) {
        StatusContent(

            text = OrderStatus.PENDING.toString(),

            selected = selected == OrderStatus.PENDING,

            onClick = { onSelected(OrderStatus.PENDING) },

            modifier = Modifier.weight(1f),

            shape = shape
        )

        StatusContent(
            text = OrderStatus.DELIVERED.toString(),

            selected = selected == OrderStatus.DELIVERED,


            onClick = { onSelected(OrderStatus.DELIVERED) },

            modifier = Modifier.weight(1f),

            shape = shape
        )

        StatusContent(
            text = OrderStatus.CANCELLED.toString(),

            selected = selected == OrderStatus.CANCELLED,


            onClick = { onSelected(OrderStatus.CANCELLED) },

            modifier = Modifier.weight(1f),

            shape = shape
        )
    }
}


@Composable
@Preview
private fun StatusPreview() {
    Status(OrderStatus.PENDING) {}
}

@Composable
private fun StatusContent(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape
) {

    val container =
        if (selected) Color.OnSurface
        else Color.SurfaceVariant

    val content =
        if (selected) Color.Surface
        else Color.OnSurfaceVariant

    Surface(
        modifier = modifier
            .height(Height.height36)
            .clickable(onClick = onClick),
        shape = shape,
        color = container
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = text, color = content, style = Typography.bodyLarge)
        }
    }
}