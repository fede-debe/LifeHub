package com.example.lifehub.ui.screens.notes.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.ui.components.ButtonPrimary
import com.example.lifehub.ui.components.TextInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel = hiltViewModel(),
    onAddNote: () -> Unit,
    onClickBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Top),
        topBar = {
            TopAppBar(
                title = { Text("Add note") },
                modifier = Modifier
                    .padding(bottom = LifeHubTheme.spacing.stack.medium),
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "top bar arrow back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }) { paddingValues ->
        AddNoteContent(
            uiState = uiState,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
            onSetTitle = viewModel::setTitle,
            onSetContent = viewModel::setContent,
            onClickAddNote = {
                viewModel.createNote()
                onAddNote()
            })
    }
}

@Composable
private fun AddNoteContent(
    uiState: NoteCreationUiState,
    modifier: Modifier,
    onSetTitle: (String) -> Unit,
    onSetContent: (String) -> Unit,
    onClickAddNote: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(LifeHubTheme.spacing.inset.medium),
        verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.medium)
    ) {
        TextInput(
            value = title,
            inputTitle = "Title",
            onValueChange = { input ->
                title = input
                onSetTitle(title)
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrectEnabled = true,
                imeAction = ImeAction.Next
            )
        )

        TextInput(
            value = content,
            inputTitle = "Content",
            singleLine = false,
            maxLines = 100,
            inputModifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 300.dp)
                .clip(LifeHubTheme.shapes.medium),
            onValueChange = { input ->
                content = input
                onSetContent(input)
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrectEnabled = true,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )

        ButtonPrimary(
            onClick = onClickAddNote,
            enabled = uiState.isNoteFilled,
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Text(text = "Add note")
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xD1D5DB)
@Composable
private fun AddNotesScreenPreview() {
    LifeHubTheme {
        AddNoteScreen(onAddNote = {}, onClickBack = {})
    }
}