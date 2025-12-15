package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.repository.WorkoutRepository
import javax.inject.Inject

class DeleteWorkoutItemUseCase @Inject constructor(
    private val repo: WorkoutRepository
) {
    suspend operator fun invoke(dateKey: String, exerciseId: String) =
        repo.deleteWorkoutItem(dateKey, exerciseId)
}