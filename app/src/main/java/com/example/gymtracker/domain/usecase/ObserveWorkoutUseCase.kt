package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.repository.WorkoutRepository
import javax.inject.Inject

class ObserveWorkoutUseCase @Inject constructor(
    private val repo: WorkoutRepository
) {
    operator fun invoke(dateKey: String) = repo.observeWorkout(dateKey)
}