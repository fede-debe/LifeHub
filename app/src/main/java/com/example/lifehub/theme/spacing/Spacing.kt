package com.example.lifehub.theme.spacing

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SpacingValues(
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp? = null
)

object Spacing {
    val spacing4 = 4.dp
    val spacing8 = 8.dp
    val spacing12 = 12.dp
    val spacing16 = 16.dp
    val spacing24 = 24.dp
    val spacing32 = 32.dp
    val spacing40 = 40.dp
    val spacing48 = 48.dp
}

@Immutable
data class LifeHubSpacing(
    val inset: SpacingValues,
    val stack: SpacingValues,
    val inline: SpacingValues
)

val LocalLifeHubSpacing = staticCompositionLocalOf {
    LifeHubSpacing(
        inset = SpacingValues(
            extraSmall = Spacing.spacing8,
            small = Spacing.spacing12,
            medium = Spacing.spacing16,
            large = Spacing.spacing24
        ),
        stack = SpacingValues(
            extraSmall = Spacing.spacing4,
            small = Spacing.spacing8,
            medium = Spacing.spacing16,
            large = Spacing.spacing24,
            extraLarge = Spacing.spacing48
        ),
        inline = SpacingValues(
            extraSmall = Spacing.spacing4,
            small = Spacing.spacing8,
            medium = Spacing.spacing16,
            large = Spacing.spacing24,
            extraLarge = Spacing.spacing48
        )
    )
}
