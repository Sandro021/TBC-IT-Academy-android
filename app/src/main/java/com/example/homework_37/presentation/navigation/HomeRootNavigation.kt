package com.example.homework_37.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.homework_37.presentation.screen.home.navigation.HomeRoute
import com.example.homework_37.presentation.screen.home.navigation.homeNavGraph


@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        homeNavGraph()
    }
}