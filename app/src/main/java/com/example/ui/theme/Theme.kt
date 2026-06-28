package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),        // Vibrant Purple primary for Dark Mode
    secondary = Color(0xFF888888),      // Medium grey
    tertiary = Color(0xFFCCCCCC),       // Light grey
    background = Color(0xFF000000),     // Black background
    surface = Color(0xFF121212),        // Near black/dark grey surface
    onPrimary = Color(0xFFFFFFFF),      // White text on purple primary
    onSecondary = Color(0xFFFFFFFF),
    onBackground = Color(0xFFFFFFFF),   // White text
    onSurface = Color(0xFFFFFFFF),      // White text
    surfaceVariant = Color(0xFF1E1E1E),
    onSurfaceVariant = Color(0xFFCCCCCC),
    outline = Color(0xFF333333)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF9C27B0),        // Deep Purple primary for Light Mode
    secondary = Color(0xFF555555),      // Dark grey
    tertiary = Color(0xFF888888),       // Medium grey
    background = Color(0xFF000000),     // Black background
    surface = Color(0xFF121212),        // Near black/dark grey surface
    onPrimary = Color(0xFFFFFFFF),      // White text on purple primary
    onSecondary = Color(0xFFFFFFFF),
    onBackground = Color(0xFFFFFFFF),   // White text
    onSurface = Color(0xFFFFFFFF),      // White text
    surfaceVariant = Color(0xFF1E1E1E),
    onSurfaceVariant = Color(0xFFCCCCCC),
    outline = Color(0xFF333333)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
