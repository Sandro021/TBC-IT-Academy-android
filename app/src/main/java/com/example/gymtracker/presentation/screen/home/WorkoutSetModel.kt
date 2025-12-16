package com.example.gymtracker.presentation.screen.home

data class WorkoutSetModel(
    val exerciseId: String,
    val number: Int,
    val weight: String = "",
    val reps: String = ""
)