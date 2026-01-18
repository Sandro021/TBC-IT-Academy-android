package com.example.homework_33.domain.model

data class Event(
    val id: String,
    val title: String,
    val category: EventCategory,
    val price: Int,
    val image: String? = ""
)
