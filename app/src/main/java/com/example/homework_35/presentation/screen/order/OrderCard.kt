package com.example.homework_35.presentation.screen.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homework_35.R
import com.example.homework_35.domain.model.Order
import com.example.homework_35.domain.model.OrderStatus
import com.example.homework_35.presentation.ui.theme.Color
import com.example.homework_35.presentation.ui.theme.Padding
import com.example.homework_35.presentation.ui.theme.Radius
import com.example.homework_35.presentation.ui.theme.Spacing
import com.example.homework_35.presentation.ui.theme.Typography
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun OrderCard(
    order: Order,
    onDetails: () -> Unit
) {
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, Radius.radius16),
        shape = Radius.radius16,
        color = Color.Surface
    ) {
        Column(Modifier.padding(Padding.padding16)) {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                Text(stringResource(R.string.order, order.orderId), fontWeight = FontWeight.Bold)

                Text(order.date.format(dateFormatter), style = Typography.bodySmall)
            }

            Spacer(Spacing.spacing10)

            Row {
                Text(

                    stringResource(R.string.tracking_number),

                    style = Typography.bodySmall
                )
                Text(

                    order.trackingNumber,

                    fontWeight = FontWeight.SemiBold,

                    style = Typography.bodySmall
                )
            }
            Spacer(Spacing.spacing10)

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                Text(
                    stringResource(R.string.quantity, order.quantity),

                    style = Typography.bodySmall
                )
                Text(
                    stringResource(R.string.subtotal, order.subtotal),

                    fontWeight = FontWeight.SemiBold,

                    style = Typography.bodySmall
                )
            }
            Spacer(Spacing.spacing10)
            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.CenterVertically

            ) {
                val statusColor = when (order.status) {

                    OrderStatus.PENDING -> Color.Pending

                    OrderStatus.DELIVERED -> Color.Delivered

                    OrderStatus.CANCELLED -> Color.Cancelled

                }

                Text(
                    text = order.status.name,

                    color = statusColor,

                    fontWeight = FontWeight.SemiBold
                )

                OutlinedButton(
                    onClick = onDetails,

                    shape = Radius.radius24,

                    contentPadding = PaddingValues(
                        horizontal = Padding.padding18,

                        vertical = Padding.padding6
                    )

                ) {
                    Text(stringResource(R.string.details))
                }
            }
        }
    }

}


@Composable
@Preview
private fun OrderCardPreview() {
    OrderCard(
        Order(
            3,
            stringResource(R.string.tracking_number), 3, OrderStatus.PENDING, LocalDate.now(), 3
        )
    ) {}
}