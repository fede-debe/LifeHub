package com.example.lifehub.theme.color

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class LifeHubeColor(
    val textColorSecondary: Color
)

val LocalLifeHubColor = staticCompositionLocalOf {
    LifeHubeColor(
        textColorSecondary = textSecondaryLight,
    )
}

val lightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceContainerHighest = surfaceLight,
    outline = outlineLight,
    error = errorLight,
    onError = onErrorLight
)

val darkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    outline = outlineDark,
    error = errorDark,
    onError = onErrorDark
)