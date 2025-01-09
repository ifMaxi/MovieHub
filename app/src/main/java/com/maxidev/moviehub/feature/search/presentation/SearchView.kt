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
import com.maxidev.moviehub.common.presentation.theme.dmSansFont
import com.maxidev.moviehub.common.presentation.theme.nunitoFont
import com.maxidev.moviehub.feature.components.ImageItem
import com.maxidev.moviehub.feature.components.SearchBarItem
import com.maxidev.moviehub.feature.navigation.NavDestinations
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.roundToInt

/**
 * A composable function representing the search view screen.
 *
 * This composable handles the display of the search UI, interaction with the
 * search view model, and navigation to the detail screen.
 *
 * @param navController The NavController used for navigation between screens.
 * @param viewModel The SearchViewModel responsible for managing the search state and logic.
 *                  Defaults to a hiltViewModel instance.
 */
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

/**
 * Displays the search content, including the search bar and the list of search results.
 *
 * This composable function manages the UI for searching and displaying results. It includes
 * a search bar for user input, and a lazy column to display the results fetched from a
 * paginated data source. It also handles different loading states and error scenarios.
 *
 * @param state The [SearchState] containing the search results and related data.
 * @param input The current text input in the search bar.
 * @param isExpanded A boolean indicating whether the search bar is expanded.
 * @param clearText A callback function to clear the text in the search bar.
 * @param onSearch A callback function triggered when the user initiates a search.
 *        It receives the search query as a parameter.
 * @param onInputChange A callback function triggered when the user changes the text input.
 *        It receives the new input as a parameter */
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
                        fontFamily = nunitoFont,
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
                                    fontFamily = nunitoFont,
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
                                    text = stringResource(R.string.something_wrong),
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

/**
 * Composable function that displays a single result item, typically for a movie or TV show.
 *
 * This function displays a card with the item's poster, name, a brief overview, and the average vote.
 * It's designed to be used within a list of results, and it provides a clickable area to navigate to
 * the details page for the selected item.
 *
 * @param modifier Modifier to be applied to the root Box of the item.
 * @param name The name of the item (e.g., movie title).
 * @param overview A brief description of the item.
 * @param posterPath The URL or path to the item's poster image.
 * @param voteAverage The average vote for the item (out of 10).
 * @param navigateToDetail A lambda function to be invoked when the item is clicked, typically used
 *                         to navigate to the item's detail screen.
 */
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
                        fontFamily = dmSansFont,
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
                        fontFamily = nunitoFont,
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
                        fontFamily = nunitoFont,
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