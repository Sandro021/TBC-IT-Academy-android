package com.example.homework_36.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TourDto(
    val title: String,
    val location: String,
    val number: String,
    val photo: String,
    val price: Int,
    val stars: Int
)