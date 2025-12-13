package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.repository.ExerciseGroupRepository
import javax.inject.Inject

class SeedExerciseGroupsUseCase @Inject constructor(
    private val repo: ExerciseGroupRepository
) {
    suspend operator fun invoke() {
        repo.seedDefaultGroupsIfEmpty()
    }
}