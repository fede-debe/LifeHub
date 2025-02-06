package com.example.lifehub.util

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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


@Composable
fun EmptyState(
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(
        LifeHubTheme.spacing.stack.small)) {
        Icon(
            painter = painterResource(iconRes),
            tint = Color.Black,
            contentDescription = "empty state icon"
        )
        Text(text = "!!! Info text todo !!!", textAlign = TextAlign.Center)
        Button(onClick = onClick) {
            Text(text = "Button", textAlign = TextAlign.Center)
        }
    }
}
