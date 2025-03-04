package com.example.lifehub.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lifehub.theme.LifeHubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    sheetState: SheetState,
    title: @Composable ColumnScope.() -> Unit,
    description: @Composable ColumnScope.() -> Unit,
    confirmButtonText: @Composable () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LifeHubTheme.spacing.inset.medium),
            verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.extraLarge)
        ) {
            Column(
                modifier = Modifier.padding(top = LifeHubTheme.spacing.inset.extraSmall),
                verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.extraSmall)
            ) {
                title()
                description()
            }
            ButtonPrimary(
                modifier = Modifier.fillMaxWidth(),
                onClick = onConfirm
            ) {
                confirmButtonText()
            }
        }
    }
}