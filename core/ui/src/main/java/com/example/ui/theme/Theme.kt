package com.example.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color


private val LightMallColors = MallColors(
    brandPrimary = Color(0xFF2A4BA0),
    brandSecondary = Color(0xFFFF6E40),
    specialPromo = Color(0xFFF9B023),
    textPrimary = Color(0xFF1B1C1E),
    background = Color(0xFFF8F9FB),
    success = Color(0xFF00C853),
    warning = Color(0xFFFFAB00)
)

private val DarkMallColors = MallColors(
    brandPrimary = Color(0xFF5E81F4),
    brandSecondary = Color(0xFFFF8A65),
    specialPromo = Color(0xFFFFD54F),
    textPrimary = Color(0xFFE0E0E0),
    background = Color(0xFF121212),
    success = Color(0xFF69F0AE),
    warning = Color(0xFFFFD180)
)

@Composable
fun MallTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkMallColors else LightMallColors
    val spacing = MallSpacing()
    val typography = DefaultMallTypography


    CompositionLocalProvider(
        LocalMallColors provides colors,
        LocalMallSpacing provides spacing,
        LocalMallTypography provides typography

    ) {
        MaterialTheme(
            colorScheme = if (darkTheme) {

                androidx.compose.material3.darkColorScheme(
                    primary = colors.brandPrimary,
                    secondary = colors.brandSecondary,
                    background = colors.background,
                    surface = colors.background
                )
            } else {

                androidx.compose.material3.lightColorScheme(
                    primary = colors.brandPrimary,
                    secondary = colors.brandSecondary,
                    background = colors.background,
                    surface = colors.background
                )
            },
            content = content
        )
    }
}

// Create a singleton object for easy access
object MallTheme {
    val colors: MallColors
        @Composable
        get() = LocalMallColors.current

    val spacing: MallSpacing
        @Composable
        get() = LocalMallSpacing.current
    val typography: MallTypography
        @Composable
        get() = LocalMallTypography.current
}