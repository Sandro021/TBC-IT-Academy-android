package com.example.homework_36.presentation.screen.tour.contract

sealed interface TourIntent {
    data object LoadTours : TourIntent
}