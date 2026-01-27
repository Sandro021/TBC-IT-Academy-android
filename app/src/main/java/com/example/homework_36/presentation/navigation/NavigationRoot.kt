package com.example.homework_36.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.homework_36.presentation.screen.tour.TourScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavigationRoot() {

    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.TourScreen::class, Route.TourScreen.serializer())

                }
            }
        },
        Route.TourScreen
    )

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Route.TourScreen> {
                TourScreen()
            }
        }
    )
}