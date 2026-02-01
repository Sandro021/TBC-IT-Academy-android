package com.example.homework_38.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.homework_38.presentation.screen.registration.navigation.RegistrationRoute
import com.example.homework_38.presentation.screen.registration.navigation.registrationNavGraph


@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = RegistrationRoute,
        modifier = modifier
    ) {
        registrationNavGraph()
    }
}