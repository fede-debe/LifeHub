package com.example.lifehub.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.lifehub.R
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.ui.components.GridLayout
import com.example.lifehub.ui.components.UiStateScreenContainer
import com.example.lifehub.util.DynamicImage

@Composable
fun HomeScreen(onClickCategory: () -> Unit, onClickLists: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Top),
    ) { innerPadding ->
        HomeScreenContent(
            modifier = Modifier.padding(innerPadding),
            onClickCategory = onClickCategory,
            onClickLists = onClickLists
        )
    }
}

@Composable
private fun HomeScreenContent(modifier: Modifier, onClickCategory: () -> Unit, onClickLists: () -> Unit) {
    UiStateScreenContainer(
        loading = false,
        modifier = modifier,
        empty = false,
        emptyContent = {},
        onRefresh = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = LifeHubTheme.spacing.inset.medium
                ),
            verticalArrangement = Arrangement.spacedBy(LifeHubTheme.spacing.stack.medium)
        ) {
            DynamicImage(image = null, placeholderText = "LifeHub")
            GridLayout(
                items = listOf("Notes", "List", "Places", "Reminders"),
                columnSpacing = LifeHubTheme.spacing.stack.medium,
                rowSpacing = LifeHubTheme.spacing.inline.medium,
                columns = 2
            ) { _, item ->
                CategoryCard(item, onClickCategory = {
                    if (item.contains("List")) {
                        onClickLists()
                    } else {
                        onClickCategory()
                    }
                })
            }
        }
    }
}

@Composable
fun CategoryCard(
    name: String,
    onClickCategory: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1.5f)
            .clip(LifeHubTheme.shapes.medium)
            .clickable {
                onClickCategory()
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = null)
                    .apply(block = fun ImageRequest.Builder.() {
                        placeholder(R.drawable.image_placeholder)
                    }).build()
            ),
            contentDescription = "Category image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD7ECFF))
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}