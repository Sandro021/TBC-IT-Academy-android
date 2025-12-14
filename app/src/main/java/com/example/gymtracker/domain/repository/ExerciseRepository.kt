package com.example.gymtracker.domain.repository

interface ExerciseRepository {
    suspend fun createExercise(name: String, groupId: String, groupTitle: String)
}