package com.example.homework_36.domain.repository

import com.example.homework_36.domain.model.Tour

interface TourRepository {
    suspend fun getTours(): List<Tour>
}