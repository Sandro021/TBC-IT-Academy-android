package com.example.homework_28.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val title : String,
    val cover : String
)