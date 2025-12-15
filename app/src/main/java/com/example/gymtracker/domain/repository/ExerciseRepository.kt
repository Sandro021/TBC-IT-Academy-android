package com.example.gymtracker.domain.repository

import com.example.gymtracker.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun createExercise(name: String, groupId: String, groupTitle: String)
    fun observeExerciseByGroup(groupId: String): Flow<List<Exercise>>
    suspend fun deleteExercise(exerciseId: String)
}