package com.example.exam_6.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkspaceDto(
    val location: String,
    @SerialName("altitude_m")
    val altitudeMeters: Int,
    val title: String,
    @SerialName("image")
    val imageUrl: String,
    val stars: Int
)
