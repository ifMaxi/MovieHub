package com.maxidev.moviehub.feature.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A composable function that displays a horizontally scrollable row of items.
 *
 * This function utilizes `LazyRow` to efficiently render a list of items in a horizontal layout.
 * It provides customization for item keys and content.
 *
 * @param modifier The modifier to be applied to the container `Box`. Defaults to `Modifier`.
 * @param items The list of items to be displayed in the row.
 * @param key A function that defines a unique key for each item. This is crucial for `LazyRow`
 *            to efficiently handle item changes (additions, removals, movements). The key should
 *            uniquely identify each item in the list.
 * @param content A composable function that defines how each item in the list should be rendered.
 *                It receives the item as a parameter.
 *
 * Example usage:
 * ```
 * data class MyItem(val id: Int, val name: String)
 *
 * val myItems = listOf(
 *     MyItem(1, "Item 1"),
 *     MyItem(2, "Item 2"),
 *     MyItem(3, "Item 3")
 * )
 *
 * RowLazyItem(
 *     items = myItems,
 *     key = { it.id }
 * ) { item ->
 *     Text(text = item.name)
 * }
 * ```
 * In the example provided we use the id of each item as the key.
 */
@Composable
fun <T: Any> RowLazyItem(
    modifier: Modifier = Modifier,
    items: List<T>,
    key: ((item: T) -> Any),
    content: @Composable (T) -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(
                items = items,
                key = { key(it) }
            ) {
                content(it)
            }
        }
    }
}