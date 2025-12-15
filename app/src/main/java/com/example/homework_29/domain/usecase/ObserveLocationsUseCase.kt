package com.example.homework_29.domain.usecase

import com.example.homework_29.domain.model.LocationItem
import com.example.homework_29.domain.repository.LocationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLocationsUseCase @Inject constructor(
    private val repo: LocationsRepository
) {
    operator fun invoke(): Flow<List<LocationItem>> = repo.observeLocations()
}