package com.example.exam_8.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {


    @Serializable
    data object ChatScreen : Route
}