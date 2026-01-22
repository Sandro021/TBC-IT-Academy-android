package com.example.homework_35.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.homework_35.data.model.OrderDto
import com.example.homework_35.domain.model.Order
import com.example.homework_35.domain.model.OrderStatus
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun OrderDto.toDomain(): Order {
    val statusEnum = runCatching { OrderStatus.valueOf(status.uppercase()) }.getOrElse {
        { OrderStatus.PENDING }
    }
    return Order(
        orderId = orderId,
        trackingNumber = trackingNumber,
        quantity = quantity,
        status = statusEnum as OrderStatus,
        date = LocalDate.parse(date),
        subtotal = subtotal
    )
}