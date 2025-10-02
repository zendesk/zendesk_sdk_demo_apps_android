package com.example.zendesk_sdk_navigation_api_demo.ui.theme

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

// Light theme color scheme
private val LightColorScheme = lightColorScheme(
    primary = ZendeskPrimaryLight,
    secondary = ZendeskSecondaryLight,
    tertiary = ZendeskTertiaryLight,
    error = ErrorLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    background = ZendeskSecondaryLight,
)

// Dark theme color scheme
private val DarkColorScheme = darkColorScheme(
    primary = ZendeskPrimaryDark,
    secondary = ZendeskSecondaryDark,
    tertiary = ZendeskTertiaryDark,
    error = ErrorDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    background = ZendeskSecondaryDark,
)

@Composable
fun ZendeskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    // Set the status bar color to match the theme
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