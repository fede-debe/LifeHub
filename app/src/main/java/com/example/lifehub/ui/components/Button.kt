package com.example.lifehub.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifehub.theme.LifeHubTheme

/**
 * A primary button styled using Material 3's [Button]. This button is used for key actions
 * that require the user's attention, such as submitting forms or proceeding to the next step.
 *
 * @param onClick The callback triggered when the button is clicked.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Whether the button is enabled or disabled.
 * @param interactionSource Tracks user interactions such as clicks or hovers.
 * @param contentPadding Padding to apply inside the button.
 * @param content The composable content inside the button.
 */
@Composable
fun ButtonPrimary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = PaddingValues(horizontal = 26.dp, vertical = 10.dp),
    content: @Composable RowScope.() -> Unit
) = Button(
    onClick = onClick,
    modifier = modifier,
    colors = colors,
    enabled = enabled,
    interactionSource = interactionSource,
    content = content,
    shape = LifeHubTheme.shapes.medium,
    contentPadding = contentPadding
)

@Preview(showBackground = true, backgroundColor = 0xD1D5DB)
@Composable
fun PreviewButtonPrimary() {
    LifeHubTheme {
        ButtonPrimary(onClick = {}) {
            Text("Primary Button")
        }
    }
}

/**
 * An outlined button styled using Material 3's [OutlinedButton]. This button is used for secondary actions
 * that support the main workflow but don’t require primary emphasis.
 *
 * @param onClick The callback triggered when the button is clicked.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Whether the button is enabled or disabled.
 * @param interactionSource Tracks user interactions such as clicks or hovers.
 * @param contentPadding Padding to apply inside the button.
 * @param content The composable content inside the button.
 */
@Composable
fun ButtonOutlined(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = PaddingValues(horizontal = 26.dp, vertical = 10.dp),
    content: @Composable RowScope.() -> Unit
) = OutlinedButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    content = content,
    shape = LifeHubTheme.shapes.medium,
    contentPadding = contentPadding,
    colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.surface)
)

@Preview(showBackground = true, backgroundColor = 0xD1D5DB)
@Composable
fun PreviewButtonOutlined() {
    LifeHubTheme {
        ButtonOutlined(onClick = {}) {
            Text("Outlined Button")
        }
    }
}

/**
 * A text button styled using Material 3's [TextButton]. This button is used for low-priority or less intrusive
 * actions that don’t require a strong emphasis, such as "Cancel" or "Learn More".
 *
 * @param onClick The callback triggered when the button is clicked.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Whether the button is enabled or disabled.
 * @param interactionSource Tracks user interactions such as clicks or hovers.
 * @param contentPadding Padding to apply inside the button.
 * @param content The composable content inside the button.
 */
@Composable
fun ButtonText(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = PaddingValues(horizontal = 26.dp, vertical = 10.dp),
    content: @Composable RowScope.() -> Unit
) = TextButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    content = content,
    shape = LifeHubTheme.shapes.medium,
    contentPadding = contentPadding
)

@Preview(showBackground = true, backgroundColor = 0xD1D5DB)
@Composable
fun PreviewButtonText() {
    LifeHubTheme {
        ButtonText(onClick = {}) {
            Text("Text Button")
        }
    }
}