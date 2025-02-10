package com.example.lifehub.ui.screens.notes

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifehub.R
import com.example.lifehub.data.model.Note
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.ui.components.EmptyState
import com.example.lifehub.ui.components.UiStateScreenContainer
import java.util.Locale

@Composable
fun NotesScreen(
    @StringRes snackBarMessage: Int,
    viewModel: NotesViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onClickAddNote: () -> Unit,
    onNoteClick: (String) -> Unit,
    onSnackBarMessageDisplayed: () -> Unit,
    onClickBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            NoteScreenTopBar(onClickBack)
        }, floatingActionButton = {
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
            onNoteClick = onNoteClick,
            onClickAddNote = onClickAddNote
        )

        uiState.snackBarMessage?.let { message ->
            val snackBarText = stringResource(message)
            LaunchedEffect(snackBarHostState, viewModel, message, snackBarText) {
                snackBarHostState.showSnackbar(snackBarText)
                viewModel.snackBarMessageShown()
            }
        }

        val currentOnSnackBarMessageDisplayed by rememberUpdatedState(onSnackBarMessageDisplayed)
        LaunchedEffect(snackBarMessage) {
            if (snackBarMessage != 0) {
                viewModel.showResultMessage(snackBarMessage)
                currentOnSnackBarMessageDisplayed()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteScreenTopBar(onClickBack: () -> Unit) {
    TopAppBar(title = {
        Text(
            text = "Notes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium
        )
    }, navigationIcon = {
        IconButton(onClick = onClickBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                modifier = Modifier.padding(horizontal = LifeHubTheme.spacing.inset.extraSmall),
                contentDescription = "back arrow app bar"
            )
        }
    })
}

@Composable
fun NotesContent(
    notes: List<Note>?,
    modifier: Modifier = Modifier,
    onNoteClick: (String) -> Unit,
    onClickAddNote: () -> Unit
) {
    UiStateScreenContainer(
        loading = false,
        modifier = modifier,
        empty = notes?.isEmpty() == true,
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
        ) {
            notes?.forEach { note ->
                NoteItem(note = note, onNoteClick = onNoteClick)
            }
        }
    }
}

@Composable
private fun NoteItem(
    note: Note,
    onNoteClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNoteClick(note.id)
            },
        verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.medium)
    ) {
        Column(
            modifier = Modifier.padding(
                LifeHubTheme.spacing.inset.medium
            ),
            verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.extraSmall)
        ) {
            Text(
                text = note.title.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = LifeHubTheme.colors.textColorSecondary
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}