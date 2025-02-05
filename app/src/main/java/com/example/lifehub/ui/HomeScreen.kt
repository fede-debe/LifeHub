package com.example.lifehub.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lifehub.util.UiStateScreenContainer

@Composable
fun HomeScreen() {
    Scaffold(modifier = Modifier.fillMaxSize(),) { innerPadding ->
        HomeScreenContent(modifier = Modifier.padding(innerPadding))
    }
}


@Composable
private fun HomeScreenContent(modifier: Modifier) {
    UiStateScreenContainer(
        loading = false,
        modifier = modifier,
        empty = false,
        emptyContent = {
            // todo content
        },
        onRefresh = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 16.dp
                )
        ) {
            repeat(4) {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Title category")
//                    EmptyState(iconRes = R.drawable.baseline_attractions_24) {
//                        // todo onclick
//                    }
                }
            }
        }
    }
}