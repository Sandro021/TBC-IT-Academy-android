package com.example.gymtracker.domain.model

data class WorkoutItem(
    val exerciseId: String,
    val name: String,
    val sets: List<WorkoutSet>
)
