package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.repository.ExerciseRepository
import javax.inject.Inject

class ObserveExercisesByGroupUseCase@Inject constructor(
    private val repo: ExerciseRepository
) {
    operator fun invoke(groupId: String) = repo.observeExerciseByGroup(groupId)
}