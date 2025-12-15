package com.example.homework_29.presentation.map

import com.example.homework_29.domain.model.LocationItem

sealed interface MapIntent {
    data object ScreenOpened : MapIntent
    data object PermissionGranted : MapIntent
    data object PermissionDenied : MapIntent
    data object ZoomIn : MapIntent
    data object ZoomOut : MapIntent
    data class MarkerClicked(val item: LocationItem) : MapIntent
    data object BottomSheetDismissed : MapIntent
}