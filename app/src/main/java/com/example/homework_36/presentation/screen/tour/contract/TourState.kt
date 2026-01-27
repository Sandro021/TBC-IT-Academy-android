package com.example.homework_36.presentation.screen.tour.contract

import com.example.homework_36.domain.model.Tour

data class TourState(
    val isLoading: Boolean = false,
    val tours: List<Tour> = emptyList(),
    val error: String? = null
)