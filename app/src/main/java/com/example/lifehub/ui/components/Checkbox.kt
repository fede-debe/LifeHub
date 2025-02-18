package com.example.lifehub.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.theme.spacing.Spacing

/**
 * @param checked whether Checkbox is checked or unchecked
 * @param onCheckedChange callback to be invoked when checkbox is being clicked,
 * therefore the change of checked state in requested.  If null, then this is passive
 * and relies entirely on a higher-level component to control the "checked" state.
 * @param modifier Modifier to be applied to the layout of the checkbox
 * @param enabled whether the component is enabled or grayed out
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * interactions for this Checkbox. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe interactions and customize the
 * appearance / behavior of this Checkbox in different interactions.
 * @param content the content to display next to the checkbox
 */
@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (() -> Unit)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.selectable(
            selected = checked,
            onClick = { onCheckedChange(!checked) }
        )) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled,
            interactionSource = interactionSource,
            modifier = Modifier.padding(Spacing.spacing4)
        )
        content()
    }
}

@Preview(name = "Checkbox")
@Composable
fun CheckboxPreview() {
    LifeHubTheme {
        Column {
            Checkbox(
                checked = true,
                onCheckedChange = { },
                content = { Text(text = "Option1") })
            Checkbox(
                checked = false,
                onCheckedChange = { },
                content = { Text(text = "Option2") })
            Checkbox(
                checked = false,
                onCheckedChange = { },
                content = { Text(text = "Option3") },
                enabled = false
            )
            Checkbox(
                checked = false,
                onCheckedChange = { },
                content = { Text(text = "Option4") },
                enabled = false
            )
        }
    }
}