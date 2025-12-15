package com.example.homework_29.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Int,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String
)