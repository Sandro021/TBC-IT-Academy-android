package com.example.homework_33.presentation.navigation3

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {
    @Serializable
    data object LoginScreen : Route

    @Serializable
    data object RegistrationScreen : Route

    @Serializable
    data object RegistrationNextScreen : Route

    @Serializable
    data object HomeScreen : Route

    @Serializable
    data object EventScreen : Route
}