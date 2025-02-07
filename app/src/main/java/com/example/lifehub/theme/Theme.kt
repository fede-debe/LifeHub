package com.example.lifehub.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.lifehub.theme.color.LifeHubeColor
import com.example.lifehub.theme.color.LocalLifeHubColor
import com.example.lifehub.theme.color.darkColorScheme
import com.example.lifehub.theme.color.lightColorScheme
import com.example.lifehub.theme.shape.LifeHubShapes
import com.example.lifehub.theme.shape.LocalLifeHubShapes
import com.example.lifehub.theme.spacing.LifeHubSpacing
import com.example.lifehub.theme.spacing.LocalLifeHubSpacing
import com.example.ui.theme.AppTypography

@Composable
fun LifeHubTheme(
    darkTheme: Boolean = false,
    content: @Composable() () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

object LifeHubTheme {
    val spacing: LifeHubSpacing
        @Composable
        get() = LocalLifeHubSpacing.current

    val shapes: LifeHubShapes
        @Composable
        get() = LocalLifeHubShapes.current

    val colors: LifeHubeColor
        @Composable
        get() = LocalLifeHubColor.current
}

