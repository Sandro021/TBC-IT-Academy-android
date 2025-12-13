package com.example.gymtracker.domain.repository

import com.example.gymtracker.domain.model.ExerciseGroup
import kotlinx.coroutines.flow.Flow

interface ExerciseGroupRepository {
    suspend fun seedDefaultGroupsIfEmpty()
    fun observeGroups(): Flow<List<ExerciseGroup>>
}