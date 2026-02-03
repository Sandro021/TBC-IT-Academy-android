package com.example.homework_39.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.presentation.navigation.RegistrationRoute
import com.example.presentation.navigation.registrationNavGraph


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