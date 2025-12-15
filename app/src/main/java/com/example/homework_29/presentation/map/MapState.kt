package com.example.homework_29.presentation.map

import com.example.homework_29.domain.model.LocationItem

data class MapState(
    val hasPermission: Boolean = false,
    val isLoading: Boolean = false,
    val locations: List<LocationItem> = emptyList(),
    val selected: LocationItem? = null,
    val errorMessage: String? = null
)
