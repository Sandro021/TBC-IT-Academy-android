package com.example.gymtracker.domain.model

data class ExerciseGroup(
    val id: String,
    val title: String,
    val icon: String,
    val orderIndex: Int,
    val exerciseCount: Int
)
