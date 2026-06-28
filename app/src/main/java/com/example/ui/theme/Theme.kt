package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryGray,
    tertiary = TertiaryBrown,
    background = OnSurfaceText,
    surface = OnSurfaceText,
    onPrimary = AppSurface,
    onSecondary = AppSurface,
    onBackground = AppBackground,
    onSurface = AppBackground
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryGray,
    tertiary = TertiaryBrown,
    background = AppBackground,
    surface = AppSurface,
    onPrimary = AppSurface,
    onSecondary = AppSurface,
    onBackground = OnSurfaceText,
    onSurface = OnSurfaceText,
    surfaceVariant = ContainerLow,
    onSurfaceVariant = OnSurfaceVariantText,
    outline = OutlineVariant
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    // We disable dynamicColor to enforce the NutriTrack corporate modern primary blue theme.
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
