package com.example.lifehub.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lifehub.theme.LifeHubTheme

@Composable
fun TextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    inputModifier: Modifier = Modifier,
    errorText: String? = null,
    maxCharacters: Int? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    inputTitle: String? = null,
    labelText: String? = null,
    isOptional: Boolean = false,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = {
        SupportingTextInput(
            errorText,
            maxCharacters,
            value
        )
    },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    Column(modifier = modifier) {
        if (inputTitle != null) {
            InputLabel(text = inputTitle, isOptional = isOptional)
        }

        OutlinedTextField(
            value = value,
            onValueChange = {
                if (it.length <= (maxCharacters ?: Int.MAX_VALUE)) onValueChange(it)
            },
            isError = errorText != null,
            label = { if (labelText != null) LabelText(text = labelText) },
            placeholder = placeholder,
            modifier = inputModifier
                .fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            supportingText = supportingText,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            shape = MaterialTheme.shapes.small,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedPlaceholderColor = LifeHubTheme.colors.textColorSecondary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = Color(0xFFFBFDFF),
                focusedContainerColor = Color(0xFFFBFDFF)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputPreview() {
    LifeHubTheme {
        var text by remember { mutableStateOf("") }
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
            TextInput(
                value = text,
                onValueChange = { text = it },
                labelText = "Deposit Amount",
                placeholder = { Text(text = "Balance: 14.559 Sushi") },
                errorText = null
            )

            TextInput(
                value = text,
                inputTitle = "Deposit Amount",
                onValueChange = { text = it },
                placeholder = { Text(text = "Balance: 14.559 Sushi") },
                errorText = null
            )
        }
    }
}

/**
 * A composable that displays supporting text for an input field, such as error messages
 * or character counters.
 *
 * - If an error is present, it displays the error message on the left.
 * - If a character limit is set, it displays the character counter on the right.
 *
 * @param errorText The error message to display, or null if there’s no error.
 * @param maxCharacters The maximum allowed character count, or null if unlimited.
 * @param value The current input value, used to calculate the character count.
 */
@Composable
private fun SupportingTextInput(errorText: String?, maxCharacters: Int?, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = LifeHubTheme.spacing.stack.extraSmall),
        horizontalArrangement = if (errorText == null) Arrangement.End else Arrangement.SpaceBetween
    ) {
        if (errorText != null) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        if (maxCharacters != null) {
            Text(
                text = "${value.length} / $maxCharacters",
                style = MaterialTheme.typography.bodySmall,
                color = LifeHubTheme.colors.textColorSecondary,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSupportingTextInputWithError() {
    LifeHubTheme {
        SupportingTextInput(
            errorText = "Invalid input",
            maxCharacters = 50,
            value = "Current input"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSupportingTextInputWithoutError() {
    LifeHubTheme {
        SupportingTextInput(
            errorText = null,
            maxCharacters = 50,
            value = "Current input"
        )
    }
}

/**
 * A label for a form input using Material 3 styling.
 * @param text The text of the label.
 * @param isOptional If true, the label will have an appended "optional" text after it.
 */
@Composable
fun InputLabel(
    text: String,
    modifier: Modifier = Modifier,
    isOptional: Boolean = false
) {
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = buildAnnotatedString {
            withStyle(
                style = MaterialTheme.typography.bodyLarge.toSpanStyle().merge(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            ) {
                append(text)
            }
            if (isOptional) {
                withStyle(
                    style = MaterialTheme.typography.labelSmall.toSpanStyle().copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    append(" - ")
                    append("Optional")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun InputLabelPreview() {
    LifeHubTheme {
        InputLabel(text = "Username", isOptional = true)
    }
}

/**
 * Styled text component to use as "information" text for a form input using Material 3 styling.
 * @param text The information text to be displayed.
 */
@Composable
fun InputInfoText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = LifeHubTheme.spacing.stack.extraSmall)
    )
}

@Preview(showBackground = true)
@Composable
fun InputInfoTextPreview() {
    LifeHubTheme {
        InputInfoText(text = "Your username must be at least 8 characters long.")
    }
}

@Composable
fun LabelText(text: String) {
    Text(text = text, color = LifeHubTheme.colors.textColorSecondary)
}


@Composable
fun TodoInputBar(
    modifier: Modifier = Modifier,
    onAddButtonClick: (String) -> Unit = {}
) {
    val input = remember { mutableStateOf("") }

    Card(
        shape = LifeHubTheme.shapes.medium,
        modifier = modifier
            .padding(vertical = LifeHubTheme.spacing.inset.extraSmall)
            .height(64.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = LifeHubTheme.spacing.inset.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    letterSpacing = 0.5.sp
                ),
                value = input.value,
                onValueChange = { newText -> input.value = newText },
                placeholder = {
                    Text(
                        text = "Write your todo",
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            letterSpacing = 0.5.sp,
                            color = MaterialTheme.colorScheme.surface
                        )
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.surface,
                    disabledTextColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    unfocusedTextColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.surface,
                onClick = {
                    if (input.value.isEmpty()) return@FloatingActionButton
                    onAddButtonClick(input.value)
                    input.value = ""
                },
                shape = CircleShape,
                modifier = Modifier.size(40.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview
@Composable
fun TodoInputBarPreview() {
    LifeHubTheme {
        TodoInputBar()
    }
}


