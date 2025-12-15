package com.example.homework_29.domain.usecase

import com.example.homework_29.domain.repository.LocationsRepository
import javax.inject.Inject

class RefreshLocationsUseCase @Inject constructor(
    private val repo: LocationsRepository
) {
    suspend operator fun invoke() = repo.refresh()
}