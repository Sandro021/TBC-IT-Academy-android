package com.example.homework_35.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.homework_35.presentation.screen.order.OrderScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic


@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.OrderScreen::class, Route.OrderScreen.serializer())
                    subclass(Route.DetailsScreen::class, Route.DetailsScreen.serializer())
                }
            }
        },
        Route.OrderScreen
    )
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Route.OrderScreen> {
                OrderScreen()
            }
        }
    )
}