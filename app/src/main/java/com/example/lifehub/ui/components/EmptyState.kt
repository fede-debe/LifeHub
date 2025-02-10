package com.example.lifehub.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.lifehub.R
import com.example.lifehub.theme.LifeHubTheme

/**
 * A reusable composable that displays an empty state with an icon, descriptive text,
 * and a button for user interaction (e.g., to add new items).
 *
 * @param iconRes The drawable resource ID of the icon to display.
 * @param description The text to display below the icon, describing the empty state.
 * @param onClick The callback triggered when the user presses the action button.
 */
@Composable
fun EmptyState(
    @DrawableRes iconRes: Int,
    description: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(iconRes),
                tint = Color.Black,
                contentDescription = "empty state icon"
            )
            Text(text = description, textAlign = TextAlign.Center)
            ButtonPrimary(onClick = onClick) {
                Text(text = "Add new", textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xD1D5DB)
@Composable
fun PreviewEmptyState() {
    LifeHubTheme {
        EmptyState(
            iconRes = R.drawable.ic_circled_plus,
            description = "No items available. Add new items to get started.",
            onClick = {}
        )
    }
}
