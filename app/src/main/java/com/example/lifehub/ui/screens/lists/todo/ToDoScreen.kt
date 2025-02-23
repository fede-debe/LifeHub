package com.example.lifehub.ui.screens.lists.todo

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifehub.R
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.ui.components.BottomSheet
import com.example.lifehub.ui.components.EmptyState
import com.example.lifehub.ui.components.UiStateScreenContainer
import com.example.lifehub.ui.screens.notes.NotesResultViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(
    viewModel: TodoViewModel = hiltViewModel(),
    resultViewModel: NotesResultViewModel,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onClickItem: (String) -> Unit,
    onClickAddTodoList: () -> Unit,
    onClickBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var listId by remember { mutableStateOf("") }
    val message by resultViewModel.snackBarMessage.collectAsState()
    val snackBarText = message?.let { stringResource(id = it) }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Top),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "ToDo",
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
        }, floatingActionButton = {
            SmallFloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = onClickAddTodoList
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "fab add new note"
                )
            }
        }
    )
    { paddingValues ->
        ToDoContent(
            uiState = uiState,
            modifier = Modifier.padding(paddingValues),
            onClickItem = onClickItem,
            onLongClickItem = { id ->
                listId = id
                showBottomSheet = true
            },
            onClickAddTodoList = onClickAddTodoList
        )

        LaunchedEffect(snackBarText) {
            snackBarText?.let {
                snackBarHostState.showSnackbar(it)
                resultViewModel.snackBarMessageShown()
            }
        }

        if (showBottomSheet) {
            BottomSheet(
                sheetState = sheetState,
                title = {
                    Text(
                        text = "Delete todo",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                description = {
                    Text(
                        text = "Are you sure you want to delete this list?",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                onDismiss = {
                    showBottomSheet = false
                },
                onConfirm = {
                    scope.launch {
                        viewModel.deleteTodoList(id = listId)
                        resultViewModel.postSnackBarMessage(R.string.successfully_deleted_todoList_message)
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                },
                confirmButtonText = {
                    Text(text = "Delete")
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ToDoContent(
    uiState: TodoUiState,
    modifier: Modifier,
    onClickItem: (String) -> Unit,
    onLongClickItem: (String) -> Unit,
    onClickAddTodoList: () -> Unit
) {
    UiStateScreenContainer(
        loading = uiState.isLoading,
        modifier = modifier,
        empty = uiState.items.isEmpty(),
        emptyContent = {
            EmptyState(
                iconRes = R.drawable.ic_circled_plus,
                description = "No ToDo Lists yet",
                onClick = onClickAddTodoList
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.small)
        ) {
            uiState.items.forEach { list ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = { onClickItem(list.id) },
                            onLongClick = { onLongClickItem(list.id) }
                        ),
                    verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.small)
                ) {
                    Text(text = list.title)
                    Text(text = "${list.todoList.size} items")
                }
            }
        }
    }
}