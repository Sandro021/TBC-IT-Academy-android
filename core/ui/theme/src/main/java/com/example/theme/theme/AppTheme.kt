package com.example.theme.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.homework_38.presentation.ui.theme.AppColors
import com.example.homework_38.presentation.ui.theme.AppDimens
import com.example.homework_38.presentation.ui.theme.AppTypography
import com.example.homework_38.presentation.ui.theme.LocalAppColors
import com.example.homework_38.presentation.ui.theme.LocalAppDimens
import com.example.homework_38.presentation.ui.theme.LocalAppTypography
import com.example.homework_38.presentation.ui.theme.appTypography
import com.example.homework_38.presentation.ui.theme.darkAppColors
import com.example.homework_38.presentation.ui.theme.lightAppColors

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