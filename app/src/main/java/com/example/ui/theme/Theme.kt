package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.model.AppThemeData

@Composable
fun MissionLakshyaTheme(
    activeTheme: AppThemeData? = null,
    displayMode: String = "dark",
    content: @Composable () -> Unit
) {
    val primaryColor = activeTheme?.let { Color(it.accentColor) } ?: BrandPrimary
    val secondaryColor = activeTheme?.let { Color(it.accentSecondaryColor) } ?: BrandSecondary
    val bgColor = activeTheme?.let { Color(it.bgColor) } ?: DarkBackground
    val surfaceColor = activeTheme?.let { Color(it.bgSecondaryColor) } ?: DarkSurface

    val isLight = displayMode == "light"
    val isOled = displayMode == "oled"

    val colorScheme = when {
        isLight -> lightColorScheme(
            primary = primaryColor,
            secondary = secondaryColor,
            tertiary = GoldAccent,
            background = LightBackground,
            surface = LightSurface,
            surfaceVariant = LightSurfaceVariant,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF0F172A),
            onSurface = Color(0xFF0F172A),
            onSurfaceVariant = Color(0xFF334155)
        )
        isOled -> darkColorScheme(
            primary = primaryColor,
            secondary = secondaryColor,
            tertiary = GoldAccent,
            background = OledBackground,
            surface = OledSurface,
            surfaceVariant = OledSurfaceVariant,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White,
            onSurfaceVariant = Color(0xFFE2E8F0)
        )
        else -> darkColorScheme(
            primary = primaryColor,
            secondary = secondaryColor,
            tertiary = GoldAccent,
            background = bgColor,
            surface = surfaceColor,
            surfaceVariant = surfaceColor.copy(alpha = 0.85f),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White,
            onSurfaceVariant = Color(0xFFCBD5E1)
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isLight
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
