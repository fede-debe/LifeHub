package com.example.lifehub.ui.screens.notes.notedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifehub.data.model.Note
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.ui.components.ButtonPrimary
import com.example.lifehub.ui.components.UiStateScreenContainer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    viewModel: NoteDetailsViewModel = hiltViewModel(),
    onDeleteNote: () -> Unit,
    onClickBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Top),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.item?.title ?: "Note",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            modifier = Modifier.padding(horizontal = LifeHubTheme.spacing.inset.extraSmall),
                            contentDescription = "back arrow app bar"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showBottomSheet = true }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            modifier = Modifier.padding(horizontal = LifeHubTheme.spacing.inset.extraSmall),
                            contentDescription = "back arrow app bar"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }) { paddingValues ->
        NoteDetailsContent(
            note = uiState.item,
            modifier = Modifier
                .padding(paddingValues)
        )

        if (showBottomSheet) {

            DeleteNoteBottomSheet(sheetState = sheetState, onDismiss = {
                showBottomSheet = false
            }, onConfirmDelete = {
                scope.launch {
                    viewModel.deleteNote()
                    sheetState.hide()
                    onDeleteNote()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            })
        }
    }
}

@Composable
private fun NoteDetailsContent(note: Note?, modifier: Modifier) {
    UiStateScreenContainer(
        loading = note == null,
        modifier = modifier,
        empty = false,
        emptyContent = {},
        onRefresh = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(LifeHubTheme.spacing.inset.medium)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.medium)
        ) {
            note?.let { validNote ->
                Text(text = validNote.content, style = MaterialTheme.typography.bodyLarge)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteNoteBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirmDelete: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
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
                Text(
                    text = "Delete note",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Are you sure you want to delete this note?",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            ButtonPrimary(modifier = Modifier.fillMaxWidth(), onClick = {
                onConfirmDelete()
            }) {
                Text(text = "Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteDetailsContentPreview() {
    LifeHubTheme {
        NoteDetailsContent(
            note = Note(
                id = "1",
                title = "Title",
                content = "description"
            ),
            modifier = Modifier
        )
    }
}