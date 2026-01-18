package com.example.homework_33.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: String?,
    val title: String?,
    val category: String?,
    val price: Int?,
    val image : String?
)
