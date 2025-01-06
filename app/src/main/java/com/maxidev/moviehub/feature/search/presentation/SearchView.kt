package com.maxidev.moviehub.feature.search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.maxidev.moviehub.R
import com.maxidev.moviehub.common.presentation.theme.MovieHubTheme
import com.maxidev.moviehub.feature.components.ImageItem
import com.maxidev.moviehub.feature.components.SearchBarItem
import com.maxidev.moviehub.feature.navigation.NavDestinations
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.roundToInt

@Composable
fun SearchView(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.searchState.collectAsStateWithLifecycle()
    val input by viewModel.input
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    SearchContent(
        state = state,
        input = input,
        isExpanded = expanded,
        clearText = {
            viewModel.clearText()
            focusManager.clearFocus()
        },
        onSearch = {
            scope.launch {
                viewModel.getSearchResults(it)
                focusManager.clearFocus()
            }
        },
        onInputChange = viewModel::onInputChanged,
        onExpandedChange = { expanded = false },
        onEvent = { event ->
            when(event) {
                is SearchUiEvents.NavigateToDetail -> {
                    navController.navigate(
                        NavDestinations.DetailMovieScreen(id = event.id)
                    )
                }
            }
        }
    )
}

@Composable
private fun SearchContent(
    state: SearchState,
    input: String,
    isExpanded: Boolean,
    clearText: () -> Unit,
    onSearch: (String) -> Unit,
    onInputChange: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onEvent: (SearchUiEvents) -> Unit
) {
    val search = state.search.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            SearchBarItem(
                input = input,
                isExpanded = isExpanded,
                onInputChange = onInputChange,
                onExpandedChange = onExpandedChange,
                onSearch = onSearch,
                placeholder = {
                    Text(
                        text = stringResource(R.string.search),
                        modifier = Modifier.semantics { contentDescription = "Search" }
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                },
                trailingIcon = {
                    if (input.isNotBlank() || input.isNotEmpty()) {
                        IconButton(onClick = clearText) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = lazyListState,
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                count = search.itemCount
            ) { index ->
                val item = search[index]

                item?.let { params ->
                    ResultItem(
                        name = params.title,
                        overview = params.overview,
                        posterPath = params.posterPath,
                        voteAverage = params.voteAverage,
                        navigateToDetail = {
                            onEvent(SearchUiEvents.NavigateToDetail(id = params.id))
                        }
                    )
                }
            }

            search.loadState.let { loadStates ->
                when {
                    loadStates.refresh is LoadState.NotLoading && search.itemCount < 1 -> {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(R.string.no_data_available),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    loadStates.refresh is LoadState.Error -> {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
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
                                    text = stringResource(R.string.something_wrong),
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

@Composable
private fun ResultItem(
    modifier: Modifier = Modifier,
    name: String,
    overview: String,
    posterPath: String,
    voteAverage: Double,
    navigateToDetail: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(10.dp)
            .clickable { navigateToDetail() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.wrapContentHeight(),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(4)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (posterPath.isNotEmpty() || posterPath.isNotBlank()) {
                    ImageItem(
                        modifier = Modifier
                            .height(150.dp)
                            .aspectRatio(2f / 3f),
                        imageUrl = posterPath,
                        contentScale = ContentScale.FillBounds,
                        navigateToDetail = {}
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .semantics { contentDescription = name }
                    )
                    Text(
                        text = overview,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .semantics { contentDescription = overview }
                    )
                    Text(
                        text = "⭐ ${voteAverage.roundToInt()} / 10",
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .semantics { contentDescription = "$voteAverage" }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ResultItemPreview() {
    MovieHubTheme {
        ResultItem(
            name = "Lorem impsum",
            overview = "Lorem impsum dolor sit amet, consectetur adipiscing elit.",
            posterPath = "Image",
            voteAverage = 6.4,
            navigateToDetail = {}
        )
    }
}