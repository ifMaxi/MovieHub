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
import retrofit2.HttpException
import java.io.IOException

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