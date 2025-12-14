package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.repository.ExerciseRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

class CreateExerciseUseCase @Inject constructor(
    private val repo: ExerciseRepository
) {
    suspend operator fun invoke(name: String, groupId: String, groupTitle: String): Result<Unit> {
        val n = name.trim()
        if (n.isBlank()) return Result.failure(IllegalArgumentException("Name can not be empty"))
        return runCatching {
            repo.createExercise(name, groupId, groupTitle)
        }
    }
}