package com.example.lifehub.ui.screens.lists.todo.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifehub.data.model.TodoItem
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.ui.components.ButtonPrimary
import com.example.lifehub.ui.components.ButtonText
import com.example.lifehub.ui.components.Checkbox
import com.example.lifehub.ui.components.TextInput
import com.example.lifehub.ui.components.TodoInputBar
import com.example.lifehub.ui.components.UiStateScreenContainer
import java.util.UUID

@Composable
fun TodoAddListScreen(
    viewModel: TodoAddListViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onCreateList: () -> Unit,
    onClickBack: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    val focusManager = LocalFocusManager.current
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(
            WindowInsetsSides.Top
        ),
        topBar = {
            TodoAddListTopBar(
                uiState = uiState,
                onClickAction = {
                    focusManager.clearFocus()
                    showDialog = true
                },
                onClickBack = onClickBack
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        TodoAddListContent(
            modifier = Modifier.padding(paddingValues = paddingValues),
            onAddItem = viewModel::addTodoItem,
            onRemoveItem = viewModel::removeTodoItem
        )
    }

    if (showDialog) {
        SubmitTodoListDialog(
            uiState = uiState, onClickConfirm = {
                viewModel.createTodoList()
                showDialog = false
                onCreateList()
            },
            onClickDismiss = {
                showDialog = false
            },
            onSetTitle = viewModel::setTitle
        )
    }
}

@Composable
private fun SubmitTodoListDialog(
    uiState: TodoCreationUiState,
    onClickConfirm: () -> Unit,
    onClickDismiss: () -> Unit,
    onSetTitle: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    AlertDialog(
        onDismissRequest = onClickDismiss,
        modifier = Modifier.padding(horizontal = LifeHubTheme.spacing.inset.medium),
        containerColor = MaterialTheme.colorScheme.surface,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        confirmButton = {
            ButtonPrimary(
                enabled = uiState.isListReadyToSubmit,
                onClick = onClickConfirm
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onClickDismiss) {
                Text("Cancel")
            }
        },
        title = { Text(text = "Enter List Title", style = MaterialTheme.typography.headlineSmall) },
        text = {
                TextInput(
                    value = title,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { input ->
                        title = input
                        onSetTitle(title)
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
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TodoAddListTopBar(
    onClickBack: () -> Unit,
    uiState: TodoCreationUiState,
    onClickAction: () -> Unit
) {
    TopAppBar(title = {
        Text(
            text = "Lists",
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
    }, actions = {
        ButtonText(
            enabled = uiState.isListValid,
            onClick = onClickAction
        ) {
            Text(
                "Create",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
    )
}

@Composable
private fun TodoAddListContent(
    modifier: Modifier,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {
    UiStateScreenContainer(
        loading = false,
        modifier = modifier,
        empty = false,
        emptyContent = {},
        onRefresh = null
    ) {
        val todoItems = remember { mutableStateListOf<TodoItem>() }

        val listState = rememberLazyListState()
        val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

        LaunchedEffect(todoItems.size, keyboardHeight) {
            if (todoItems.isNotEmpty()) {
                listState.animateScrollToItem(todoItems.lastIndex)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.medium)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f),
                contentPadding = PaddingValues(LifeHubTheme.spacing.inset.medium),
                verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.medium)
            ) {
                items(todoItems) { item ->
                    TodoItemUi(
                        todoItem = item,
                        onItemClick = { /* No action during list creation */ },
                        onItemDelete = { deletedItem ->
                            todoItems.remove(deletedItem)
                            onRemoveItem(deletedItem)
                        }
                    )
                }
            }

            TodoInputBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = LifeHubTheme.spacing.inset.extraSmall)
                    .imePadding(),
                onAddButtonClick = { input ->
                    if (input.isNotBlank()) {
                        val newItem = TodoItem(id = UUID.randomUUID().toString(), content = input)
                        todoItems.add(newItem)
                        onAddItem(newItem)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TodoItemUi(
    todoItem: TodoItem = TodoItem(id = "todo", content = "Todo Item"),
    //  1. Lambda Function Parameters for Flexibility
    onItemClick: (TodoItem) -> Unit = {},
    onItemDelete: (TodoItem) -> Unit = {}
) {
    // 2. Adaptive Color Scheme
    val backgroundColor =
        if (todoItem.isDone) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onPrimary
    val textColor =
        if (todoItem.isDone) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.primary

    // 3. Text Decoration
    val textDecoration = if (todoItem.isDone) TextDecoration.LineThrough else null

    // 4. Dynamic Icons
//    val iconIdd = if (todoItem.isDone) R.drawable.ic_selected_check_box else R.drawable.ic_empty_check_box = if (todoItem.isDone) R.drawable.ic_selected_check_box else R.drawable.ic_empty_check_box
    val iconColorFilter =
        if (todoItem.isDone) ColorFilter.tint(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)) else ColorFilter.tint(
            MaterialTheme.colorScheme.background
        )
    val iconTintColor =
        if (todoItem.isDone) MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.secondary

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        shape = LifeHubTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                // 5. Clickable Modifier with Ripple Effect:
                .clickable(
                    enabled = false,
                    onClick = { onItemClick(todoItem) }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Checkbox(
                checked = false,
                enabled = false,
                onCheckedChange = {}
            ) {
                Text(
                    text = todoItem.content,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = textDecoration
                )
            }

            // 6. IconButton for Deletion
            IconButton(
                onClick = { onItemDelete(todoItem) },
                modifier = Modifier.padding(end = LifeHubTheme.spacing.inline.extraSmall)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                    tint = iconTintColor
                )
            }
        }
    }
}

@Preview
@Composable
fun TodoItemUiPreview() {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TodoItemUi(todoItem = TodoItem(id = "", content = "Todo Item 1"))
        TodoItemUi(todoItem = TodoItem(id = "", content = "Todo Item 2", isDone = true))
        TodoItemUi(todoItem = TodoItem(id = "", content = "Todo Item 3"))
        TodoItemUi(todoItem = TodoItem(id = "", content = "Todo Item 4", isDone = true))
    }
}