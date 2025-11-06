package com.example.homework_17.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardInfo(

    @SerialName("card_holder_name")
    val cardHolderName: String,


    @SerialName("card_number")
    val cardNumber: String,


    @SerialName("valid_thru")
    val validThru: String,


    @SerialName("card_type")
    val cardType: String,


    )