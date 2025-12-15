package com.example.gymtracker.domain.repository

import com.example.gymtracker.domain.model.WorkoutItem
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun observeWorkout(dateKey: String): Flow<List<WorkoutItem>>
    suspend fun saveWorkout(dateKey: String, items: List<WorkoutItem>)
    suspend fun deleteWorkoutItem(dateKey: String, exerciseId: String)
}