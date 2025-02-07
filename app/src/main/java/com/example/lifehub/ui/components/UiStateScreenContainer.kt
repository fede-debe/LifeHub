package com.example.lifehub.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.lifehub.theme.LifeHubTheme

/**
 * A reusable container composable that handles loading, empty, and valid content states
 * with pull-to-refresh support.
 *
 * @param loading When true, indicates that content is loading.
 * @param empty When true, displays the [emptyContent] slot.
 * @param emptyContent The composable to display when there's no content.
 * @param onRefresh The callback triggered when the user pulls to refresh.
 * @param modifier Modifier to be applied to the container.
 * @param content The main content to display when not empty.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiStateScreenContainer(
    loading: Boolean,
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullRefreshState,
        modifier = modifier,
        isRefreshing = loading,
        onRefresh = onRefresh
    ) {
        if (empty) {
            emptyContent()
        } else {
            content()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xD1D5DB)
@Composable
fun PreviewUiStateScreenContainer(@PreviewParameter(EmptyStateProvider::class) isEmpty: Boolean) {
    LifeHubTheme {
        UiStateScreenContainer(
            loading = false,
            empty = isEmpty,
            emptyContent = { Text("This is the empty state.") },
            onRefresh = { /* No-op for preview */ },
            modifier = Modifier,
            content = { Text("Main content goes here.") }
        )
    }
}

class EmptyStateProvider: PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(
        true,
        false
    )
}