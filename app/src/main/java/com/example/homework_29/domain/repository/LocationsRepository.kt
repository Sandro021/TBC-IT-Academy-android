package com.example.homework_29.domain.repository

import com.example.homework_29.domain.model.LocationItem
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {
    fun observeLocations(): Flow<List<LocationItem>>
    suspend fun refresh()
}