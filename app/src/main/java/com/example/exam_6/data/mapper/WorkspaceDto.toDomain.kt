package com.example.exam_6.data.mapper

import com.example.exam_6.data.dto.WorkspaceDto
import com.example.exam_6.domain.Workspace

fun WorkspaceDto.toDomain(): Workspace = Workspace(
    location = location,
    altitudeMeters = altitudeMeters,
    title = title,
    imageUrl = imageUrl,
    stars = stars
)