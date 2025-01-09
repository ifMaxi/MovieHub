package com.maxidev.moviehub.feature.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.maxidev.moviehub.R
import com.maxidev.moviehub.common.presentation.theme.nunitoFont
import retrofit2.HttpException
import java.io.IOException

/**
 * A composable function that displays a horizontal row of items fetched using paging.
 * It handles different loading states (loading, error, empty) and displays
 * corresponding UI elements.
 *
 * @param modifier The modifier to be applied to the root Box composable.
 * @param items The [LazyPagingItems] instance that holds the paged data.
 * @param key A lambda function that returns a unique key for each item. This is
 *   used for optimizing the recomposition of the list.
 * @param content A composable function that defines how each individual item
 *   should be displayed. It receives the item as a parameter.
 *
 * @param T The type of data held by the [LazyPagingItems].
 *
 * Usage example:
 * ```
 *    PagedRow(
 *       modifier = Modifier.padding(16.dp),
 *       items = myPagedItems,
 *       key = { item -> item.id }
 *   ) { item ->
 *       MyItemComposable(item)
 *   }
 * ```
 *
 * UI Behavior:
 *  - **Loading:** Displays a `CircularProgressIndicator` when initial data is loading or appending data
 *  - **Empty:** When no data is available after refresh load operation is done, it displays a text message "No data available".
 *  - **Error (Refresh):** Displays an error message when the initial data fetching fails. Specific error message is shown */
@Composable
fun <T: Any> PagedRow(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
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
                count = items.itemCount,
                key = { key(items[it]!!) }
            ) {
                val item = items[it]

                if (item != null) {
                    content(item)
                }
            }

            items.loadState.let { loadStates ->
                when {
                    loadStates.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .align(Alignment.Center),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(strokeWidth = 2.dp)
                            }
                        }
                    }

                    loadStates.refresh is LoadState.NotLoading && items.itemCount < 1 -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .align(Alignment.Center),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = stringResource(R.string.no_data_available),
                                    fontFamily = nunitoFont,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    loadStates.refresh is LoadState.Error -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .align(Alignment.Center),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = when ((loadStates.refresh as LoadState.Error).error) {
                                        is HttpException -> { stringResource(R.string.something_wrong) }
                                        is IOException -> { stringResource(R.string.internet_problem) }
                                        else -> { stringResource(R.string.unknown_error) }
                                    },
                                    fontFamily = nunitoFont,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    loadStates.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(16.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                        }
                    }

                    loadStates.append is LoadState.Error -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(R.string.error_occurred),
                                    fontFamily = nunitoFont,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}