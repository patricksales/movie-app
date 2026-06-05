package com.movieapp.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = TealGreen,
    onPrimary = TextOnPrimary,
    primaryContainer = TealGreenContainer,
    onPrimaryContainer = TealGreenDark,
    secondary = TealGreenLight,
    onSecondary = TextOnPrimary,
    background = LightBackground,
    onBackground = TextPrimary,
    surface = LightSurface,
    onSurface = TextPrimary,
    error = ErrorRed,
    onError = White,
    surfaceVariant = White,
    onSurfaceVariant = TextSecondary
)

private val DarkColorScheme = darkColorScheme(
    primary = TealGreenLight,
    onPrimary = Black,
    primaryContainer = TealGreenDark,
    onPrimaryContainer = TealGreenContainer,
    secondary = TealGreen,
    onSecondary = Black,
    background = DarkBackground,
    onBackground = White,
    surface = DarkSurface,
    onSurface = White,
    error = ErrorRed,
    onError = Black,
    surfaceVariant = DarkSurface,
    onSurfaceVariant = TextSecondary
)

@Composable
fun MovieAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
