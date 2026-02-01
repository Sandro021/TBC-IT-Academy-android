package com.example.homework_38.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AppColors(
    val background: Color,
    val cardBackground: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textHint: Color,
    val buttonBackground: Color,
    val buttonText: Color,
    val inputBackground: Color,
    val error: Color,
    val divider: Color
)

data class AppDimens(
    val paddingSmall: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingLarge: Dp = 24.dp,
    val cornerRadius: Dp = 12.dp,
    val iconSize: Dp = 24.dp
)

data class AppTypography(
    val title: TextStyle,
    val body: TextStyle,
    val label: TextStyle
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        background = Color.Unspecified,
        cardBackground = Color.Unspecified,
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        textHint = Color.Unspecified,
        buttonBackground = Color.Unspecified,
        buttonText = Color.Unspecified,
        inputBackground = Color.Unspecified,
        error = Color.Unspecified,
        divider = Color.Unspecified
    )
}

val LocalAppDimens = staticCompositionLocalOf { AppDimens() }

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        title = TextStyle.Default,
        body = TextStyle.Default,
        label = TextStyle.Default
    )
}


val lightAppColors = AppColors(
    background = AppColor.lightGray,
    cardBackground = AppColor.White,
    textPrimary = AppColor.Black,
    textSecondary = AppColor.textSecondary,
    textHint = AppColor.TextGray,
    buttonBackground = AppColor.BluePrimary,
    buttonText = AppColor.White,
    inputBackground = AppColor.transparent,
    error = AppColor.RedError,
    divider = Color.LightGray
)


val darkAppColors = AppColors(
    background = AppColor.DarkGray,
    cardBackground = AppColor.CardDark,
    textPrimary = AppColor.White,
    textSecondary = AppColor.textSecondary,
    textHint = Color.Gray,
    buttonBackground = AppColor.BluePrimary,
    buttonText = AppColor.White,
    inputBackground = AppColor.transparent,
    error = AppColor.error,
    divider = AppColor.DarkGray
)


val appTypography = AppTypography(
    title = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
    body = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    label = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, color = AppColor.TextGray)
)
