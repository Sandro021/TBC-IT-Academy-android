package com.example.homework_28.data.mapper

import com.example.homework_28.data.model.StoryDto
import com.example.homework_28.domain.model.Story

fun StoryDto.toDomain() = Story(
    title = title,
    coverUrl = cover
)