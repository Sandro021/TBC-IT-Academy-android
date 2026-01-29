package com.example.homework_37.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val id: Int,
    val title: String,
    val cover: String
)