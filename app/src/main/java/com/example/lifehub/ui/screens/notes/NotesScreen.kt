package com.example.lifehub.ui.screens.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lifehub.R
import com.example.lifehub.data.model.Note
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.ui.components.EmptyState
import com.example.lifehub.ui.components.UiStateScreenContainer

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel(),
    onClickAddNote: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState(NotesUiState())

    Scaffold(modifier = modifier.fillMaxSize(), floatingActionButton = {
        SmallFloatingActionButton(onClick = onClickAddNote) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "fab add new note"
            )
        }
    }) { paddingValues ->
        NotesContent(
            notes = uiState.items,
            modifier = Modifier.padding(paddingValues),
            onClickAddNote = onClickAddNote
        )
    }
}

@Composable
fun NotesContent(
    notes: List<Note>?,
    modifier: Modifier = Modifier,
    onClickAddNote: () -> Unit
) {
    UiStateScreenContainer(
        loading = false,
        modifier = modifier,
        empty = notes.isNullOrEmpty(),
        emptyContent = {
            EmptyState(
                iconRes = R.drawable.ic_circled_plus,
                description = "No notes yet",
                onClick = onClickAddNote
            )
        },
        onRefresh = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = LifeHubTheme.spacing.inset.medium
                ),
            verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.medium)
        ) {
            notes?.forEach { note ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.small)) {
                        Text(text = note.title)
                        Text(text = note.content)
                    }
                }
            }
        }
    }
}