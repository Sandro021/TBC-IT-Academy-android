package com.example.homework_33.presentation.navigation3

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.homework_33.presentation.screen.HomeScreen
import com.example.homework_33.presentation.screen.event.EventScreen
import com.example.homework_33.presentation.screen.login.LoginScreen
import com.example.homework_33.presentation.screen.registration.RegisterScreen
import com.example.homework_33.presentation.screen.registration_next.RegisterNextScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.EventScreen::class, Route.EventScreen.serializer())
                    subclass(Route.LoginScreen::class, Route.LoginScreen.serializer())
                    subclass(Route.RegistrationScreen::class, Route.RegistrationScreen.serializer())
                    subclass(
                        Route.RegistrationNextScreen::class,
                        Route.RegistrationNextScreen.serializer()
                    )
                    subclass(Route.HomeScreen::class, Route.HomeScreen.serializer())
                }
            }
        },
        Route.HomeScreen
    )
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Route.HomeScreen> {
                HomeScreen(
                    onLogin = { backStack.add(Route.LoginScreen) },
                    onRegister = { backStack.add(Route.RegistrationScreen) }
                )
            }
            entry<Route.LoginScreen> {
                LoginScreen(
                    onBack = { backStack.removeLast() },
                    onNext = { backStack.add(Route.EventScreen) }
                )
            }
            entry<Route.RegistrationScreen> {
                RegisterScreen(
                    onBack = { backStack.removeLast() },
                    onNext = { backStack.add(Route.RegistrationNextScreen) }
                )
            }
            entry<Route.RegistrationNextScreen> {
                RegisterNextScreen(
                    onBack = { backStack.removeLast() },
                    onSignUp = { backStack.add(Route.EventScreen) }
                )
            }
            entry<Route.EventScreen> {
                EventScreen()
            }
        }

    )
}
