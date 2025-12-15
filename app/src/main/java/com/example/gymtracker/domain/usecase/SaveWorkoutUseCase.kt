package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.model.WorkoutItem
import com.example.gymtracker.domain.repository.WorkoutRepository
import javax.inject.Inject

class SaveWorkoutUseCase @Inject constructor(
    private val repo: WorkoutRepository
) {
    suspend operator fun invoke(dateKey: String, items: List<WorkoutItem>) = runCatching {
        repo.saveWorkout(dateKey, items)
    }
}