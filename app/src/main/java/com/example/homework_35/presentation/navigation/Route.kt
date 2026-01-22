package com.example.homework_35.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {

    @Serializable
    data object OrderScreen : Route


    @Serializable
    data object DetailsScreen : Route
}