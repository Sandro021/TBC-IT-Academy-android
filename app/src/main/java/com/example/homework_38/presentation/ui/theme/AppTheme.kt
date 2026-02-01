package com.example.homework_38.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkAppColors else lightAppColors
    val dimens = AppDimens()
    val typography = appTypography


    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppDimens provides dimens,
        LocalAppTypography provides typography
    ) {

        MaterialTheme(
            content = content
        )
    }
}


object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val dimens: AppDimens
        @Composable
        get() = LocalAppDimens.current

    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current
}