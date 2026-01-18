//package com.example.homework_33.presentation.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.example.homework_33.presentation.screen.HomeScreen
//import com.example.homework_33.presentation.screen.registration_next.RegisterNextScreen
//import com.example.homework_33.presentation.screen.registration.RegisterScreen
//import com.example.homework_33.presentation.screen.event.WelcomeScreen
//import com.example.homework_33.presentation.screen.login.LoginScreen
//import com.example.homework_33.presentation.screen.registration_next.RegisterNextViewModel
//
//@Composable
//fun AppNavHost(navController: NavHostController) {
//
//    NavHost(
//        navController = navController,
//        startDestination = Routes.Home.route
//    ) {
//        composable(Routes.Welcome.route) {
//            WelcomeScreen()
//        }
//        composable(Routes.Home.route) {
//            HomeScreen(
//                onLogin = { navController.navigate(Routes.Login.route) },
//                onRegister = { navController.navigate(Routes.Register.route) }
//            )
//        }
//        composable(Routes.Login.route) {
//            LoginScreen(
//                onBack = { navController.popBackStack() },
//                onNext = { navController.navigate(Routes.Welcome.route) }
//            )
//        }
//        composable(Routes.Register.route) {
//            RegisterScreen(
//                onNext = { navController.navigate(Routes.RegisterNext.route) },
//                onBack = { navController.popBackStack() }
//            )
//        }
//        composable(Routes.RegisterNext.route) {
//            RegisterNextScreen(
//                onBack = { navController.popBackStack() },
//                onSignUp = {
//                    navController.navigate(Routes.Welcome.route) {
//                        popUpTo(Routes.Home.route) { inclusive = true }
//                    }
//                }
//            )
//        }
//    }
//}