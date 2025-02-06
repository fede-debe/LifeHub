package com.example.lifehub.theme
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.lifehub.theme.color.darkColorScheme
import com.example.lifehub.theme.color.lightColorScheme
import com.example.lifehub.theme.spacing.LifeHubSpacing
import com.example.lifehub.theme.spacing.LocalLifeHubSpacing
import com.example.ui.theme.AppTypography

@Composable
fun LifeHubTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

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
}

