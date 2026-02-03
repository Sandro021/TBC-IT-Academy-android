package com.example.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.RegistrationScreen
import kotlinx.serialization.Serializable


@Serializable
data object RegistrationRoute


fun NavGraphBuilder.registrationNavGraph() {
    composable<RegistrationRoute> {
        RegistrationScreen()
    }
}