package com.heidygonzalez.applibros.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Tema oscuro
val md_theme_dark_primary = Color(0xFFBB86FC)
val md_theme_dark_onPrimary = Color(0xFFFFFFFF)
val md_theme_dark_background = Color(0xFF121212)
val md_theme_dark_onBackground = Color(0xFFFFFFFF)
val md_theme_dark_surface = Color(0xFF1E1E1E)
val md_theme_dark_onSurface = Color(0xFFFFFFFF)

// Tema claro
val md_theme_light_primary = Color(0xFF6200EE)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_background = Color(0xFFFFFFFF)
val md_theme_light_onBackground = Color(0xFF000000)
val md_theme_light_surface = Color(0xFFFFFBFE)
val md_theme_light_onSurface = Color(0xFF000000)

// Esquema de colores oscuro
private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface
)

// Esquema de colores claro
private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface
)

@Composable
fun AppLibrosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // Colores dinámicos para Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Usa una tipografía predefinida
        content = content
    )
}
