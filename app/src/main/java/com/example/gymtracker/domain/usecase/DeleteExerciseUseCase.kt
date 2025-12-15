package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.repository.ExerciseRepository
import javax.inject.Inject

class DeleteExerciseUseCase @Inject constructor(
    private val repo: ExerciseRepository
) {
    suspend operator fun invoke(exerciseId: String) = repo.deleteExercise(exerciseId)
}