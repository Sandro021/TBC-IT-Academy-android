package com.example.gymtracker.data.firestore

data class ExerciseGroupDto(
    val title: String = "",
    val icon: String = "",
    val orderIndex: Int = 0,
    val exerciseCount: Int = 0
)
