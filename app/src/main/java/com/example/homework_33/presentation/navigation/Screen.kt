package com.example.homework_33.presentation.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object RegisterNext : Screen("register_next")
    data object Home : Screen("home")
}