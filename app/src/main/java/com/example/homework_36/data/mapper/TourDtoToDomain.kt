package com.example.homework_36.data.mapper

import com.example.homework_36.data.dto.TourDto
import com.example.homework_36.domain.model.Tour


fun TourDto.toDomain() = Tour(
    title,
    location,
    number,
    photo,
    price,
    stars
)