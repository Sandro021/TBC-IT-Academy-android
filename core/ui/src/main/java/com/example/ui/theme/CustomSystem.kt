package com.example.ui.theme



import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Immutable
data class MallColors(
    val brandPrimary: Color,
    val brandSecondary: Color,
    val specialPromo: Color, // Specific to your mall app
    val textPrimary: Color,
    val background: Color,
    val success: Color,
    val warning: Color
)


@Immutable
data class MallSpacing(
    val tiny: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp
)


val LocalMallColors = staticCompositionLocalOf {

    MallColors(
        brandPrimary = Color.Unspecified,
        brandSecondary = Color.Unspecified,
        specialPromo = Color.Unspecified,
        textPrimary = Color.Unspecified,
        background = Color.Unspecified,
        success = Color.Unspecified,
        warning = Color.Unspecified
    )
}

val LocalMallSpacing = staticCompositionLocalOf { MallSpacing() }