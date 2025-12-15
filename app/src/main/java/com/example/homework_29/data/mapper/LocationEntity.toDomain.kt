package com.example.homework_29.data.mapper

import com.example.homework_29.data.room.LocationEntity
import com.example.homework_29.domain.model.LocationItem

fun LocationEntity.toDomain() = LocationItem(
    id, title, description, latitude, longitude, imageUrl
)