package com.example.homework_29.data.mapper

import com.example.homework_29.data.model.LocationDto
import com.example.homework_29.data.room.LocationEntity

fun LocationDto.toEntity() = LocationEntity(id, title, description, latitude, longitude, imageUrl)
