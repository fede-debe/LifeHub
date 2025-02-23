package com.example.lifehub.ui.screens.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifehub.R
import com.example.lifehub.data.model.Note
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.ui.components.EmptyState
import com.example.lifehub.ui.components.UiStateScreenContainer
import com.example.lifehub.ui.screens.notes.notedetails.DeleteNoteBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    resultViewModel: NotesResultViewModel,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onClickAddNote: () -> Unit,
    onNoteClick: (String) -> Unit,
    onClickBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val message by resultViewModel.snackBarMessage.collectAsState()
    val snackBarText = message?.let { stringResource(id = it) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var noteId by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Top),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            NoteScreenTopBar(scrollBehavior = scrollBehavior, onClickBack = onClickBack)
        }, floatingActionButton = {
            SmallFloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = onClickAddNote
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "fab add new note"
                )
            }
        }) { paddingValues ->

        NotesContent(
            uiState = uiState,
            modifier = Modifier
                .padding(paddingValues),
            onNoteClick = onNoteClick,
            onClickAddNote = onClickAddNote,
            onNoteLongClick = { id ->
                noteId = id
                showBottomSheet = true
            })

        LaunchedEffect(snackBarText) {
            snackBarText?.let {
                snackBarHostState.showSnackbar(it)
                resultViewModel.snackBarMessageShown()
            }
        }

        if (showBottomSheet) {
            DeleteNoteBottomSheet(sheetState = sheetState, onDismiss = {
                showBottomSheet = false
            }, onConfirmDelete = {
                scope.launch {
                    viewModel.deleteNote(noteId = noteId)
                    resultViewModel.postSnackBarMessage(R.string.successfully_deleted_note_message)
                    sheetState.hide()
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
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteScreenTopBar(scrollBehavior: TopAppBarScrollBehavior, onClickBack: () -> Unit) {
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
    },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun NotesContent(
    uiState: NotesUiState,
    modifier: Modifier = Modifier,
    onNoteClick: (String) -> Unit,
    onNoteLongClick: (String) -> Unit,
    onClickAddNote: () -> Unit,
) {
    UiStateScreenContainer(
        loading = uiState.isLoading,
        modifier = modifier,
        empty = uiState.items.isEmpty() && !uiState.isLoading,
        emptyContent = {
            EmptyState(
                iconRes = R.drawable.ic_circled_plus,
                description = "No notes yet",
                onClick = onClickAddNote
            )
        },
        onRefresh = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
        ) {
            uiState.items.forEach { note ->
                NoteItem(note = note, onNoteClick = onNoteClick, onNoteLongClick = onNoteLongClick)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NoteItem(
    note: Note,
    onNoteClick: (String) -> Unit,
    onNoteLongClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onNoteClick(note.id) },
                onLongClick = { onNoteLongClick(note.id) }
            ),
        verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.small)
    ) {
        Column(
            modifier = Modifier.padding(
                LifeHubTheme.spacing.inset.medium
            ),
            verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.extraSmall)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = LifeHubTheme.colors.textColorSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}