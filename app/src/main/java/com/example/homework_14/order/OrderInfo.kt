package com.example.homework_14.order

data class OrderInfo(
    val id : Int,
    val trackingNumber : String ,
    val quantity : Int ,
    val subTotal : Double ,
    val date : String ,
    val status : OrderStatus
)
enum class OrderStatus {
    PENDING,
    DELIVERED,
    CANCELLED
}
