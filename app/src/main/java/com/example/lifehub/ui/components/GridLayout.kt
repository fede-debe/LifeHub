package com.example.lifehub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lifehub.theme.LifeHubTheme
import com.example.lifehub.ui.screens.home.CategoryCard

/**
 * A customizable grid layout composable that arranges items in a specified number of columns
 * with spacing between rows and columns.
 *
 * @param items The list of items to display in the grid.
 * @param columns The number of columns in the grid.
 * @param modifier Modifier to be applied to the grid container.
 * @param columnSpacing The horizontal spacing between columns.
 * @param rowSpacing The vertical spacing between rows.
 * @param content The content to be displayed for each grid item, provided with the item index and data.
 */
@Composable
fun <T> GridLayout(
    items: List<T>,
    columns: Int,
    modifier: Modifier = Modifier,
    columnSpacing: Dp = 8.dp,
    rowSpacing: Dp = 0.dp,
    content: @Composable (Int, T) -> Unit
) {
    val itemCount = items.size
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(columnSpacing)) {
        var rows = (itemCount / columns)
        if (itemCount % columns > 0) {
            rows += 1
        }

        for (rowId in 0 until rows) {
            val firstIndex = rowId * columns
            Row(horizontalArrangement = Arrangement.spacedBy(rowSpacing)) {
                for (columnId in 0 until columns) {
                    val index = firstIndex + columnId
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        if (index < itemCount) {
                            content(index, items[index])
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xD1D5DB)
@Composable
fun PreviewGridLayout() {
    LifeHubTheme {
        GridLayout(
            items = List(6) { "Item $it" },
            columns = 2,
            columnSpacing = 16.dp,
            rowSpacing = 12.dp
        ) { _, item ->
           CategoryCard(item) { }
        }
    }
}