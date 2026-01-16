package com.example.homework_33.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.homework_33.presentation.screen.HomeScreen
import com.example.homework_33.presentation.screen.RegisterNextScreen
import com.example.homework_33.presentation.screen.RegisterScreen
import com.example.homework_33.presentation.screen.WelcomeScreen
import com.example.homework_33.presentation.screen.login.LoginScreen

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen()
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onLogin = { navController.navigate(Screen.Login.route) },
                onRegister = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Screen.Welcome.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onNext = { navController.navigate(Screen.RegisterNext.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.RegisterNext.route) {
            RegisterNextScreen(
                onBack = { navController.popBackStack() },
                onSignUp = { navController.navigate(Screen.Welcome.route) }
            )
        }
    }
}