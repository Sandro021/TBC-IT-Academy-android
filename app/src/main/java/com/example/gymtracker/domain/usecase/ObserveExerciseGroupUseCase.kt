package com.example.gymtracker.domain.usecase

import com.example.gymtracker.domain.model.ExerciseGroup
import com.example.gymtracker.domain.repository.ExerciseGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveExerciseGroupUseCase @Inject constructor(
    private val repo: ExerciseGroupRepository
) {
    operator fun invoke(): Flow<List<ExerciseGroup>> = repo.observeGroups()
}