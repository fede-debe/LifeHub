package com.example.lifehub.ui.screens.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.ui.components.GridLayout
import com.example.lifehub.ui.components.UiStateScreenContainer
import com.example.lifehub.ui.screens.home.CategoryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onClickListType: (String) -> Unit,
    onClickBack: () -> Unit
) {
    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(
            WindowInsetsSides.Top
        ),
        topBar = {
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
            }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        ListsContent(modifier = Modifier.padding(paddingValues = paddingValues), onClickListType = onClickListType)
    }
}

@Composable
private fun ListsContent(modifier: Modifier = Modifier, onClickListType: (String) -> Unit,) {
    UiStateScreenContainer(
        loading = false,
        modifier = modifier,
        empty = false,
        emptyContent = {},
        onRefresh = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(horizontal = LifeHubTheme.spacing.inset.medium)
        ) {
            GridLayout(
                items = listOf("ToDo", "Grocery", "Shopping", "Personal"),
                columns = 2,
                columnSpacing = LifeHubTheme.spacing.stack.medium,
                rowSpacing = LifeHubTheme.spacing.inline.medium,
            ) { _, item ->
                CategoryCard(item, onClickCategory = { onClickListType(item)})
            }
        }
    }
}