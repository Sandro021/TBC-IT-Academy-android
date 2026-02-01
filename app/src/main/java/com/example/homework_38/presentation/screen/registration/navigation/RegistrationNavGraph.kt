package com.example.homework_38.presentation.screen.registration.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.homework_38.presentation.screen.registration.RegistrationScreen
import kotlinx.serialization.Serializable

@Serializable
data object RegistrationRoute


fun NavGraphBuilder.registrationNavGraph() {
    composable<RegistrationRoute> {
        RegistrationScreen()
    }
}