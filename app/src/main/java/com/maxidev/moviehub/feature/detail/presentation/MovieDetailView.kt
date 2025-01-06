@file:OptIn(ExperimentalMaterial3Api::class)

package com.maxidev.moviehub.feature.detail.presentation

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.maxidev.moviehub.feature.components.TopBarItem
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail
import com.maxidev.moviehub.feature.detail.presentation.components.BelongsToCollectionItem
import com.maxidev.moviehub.feature.detail.presentation.components.GenresItem
import com.maxidev.moviehub.feature.detail.presentation.components.OtherInformationItem
import com.maxidev.moviehub.feature.detail.presentation.components.OverviewItem
import com.maxidev.moviehub.feature.detail.presentation.components.PosterWithTextItem
import com.maxidev.moviehub.feature.detail.presentation.components.ProductionCompaniesItem
import kotlinx.coroutines.launch

@Composable
fun MovieDetailView(
    id: Int,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.detailState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite(id).collectAsStateWithLifecycle(initialValue = false)
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, state.movieDetail?.homePage)
        type = "text/plain"
    }
    val chooser = Intent.createChooser(sendIntent, "Share")
    val context = LocalContext.current

    LaunchedEffect(Int) {
        viewModel.fetchMovieDetail(id)
    }

    DetailItem(
        isLiked = isFavorite,
        state = state,
        scrollBehavior = scrollBehavior,
        onEvent = { events ->
            when(events) {
                is MovieDetailUiEvents.AddToFavorites -> {
                    viewModel.saveFavorite(events.add)
                }
                is MovieDetailUiEvents.RemoveToFavorites -> {
                    viewModel.deleteFavorite(events.remove)
                }
                MovieDetailUiEvents.NavigateBack -> {
                    navController.popBackStack()
                }
                is MovieDetailUiEvents.ShareIntent -> {
                    context.startActivity(chooser) // TODO: Fix intent.
                }
            }
        }
    )
}

@Composable
private fun DetailItem(
    isLiked: Boolean,
    state: MovieDetailState,
    onEvent: (MovieDetailUiEvents) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val detail = state.movieDetail

    ScreenContent(
        isLiked = isLiked,
        model = detail ?: return,
        scrollBehavior = scrollBehavior,
        onEvent = onEvent
    )
}

@Composable
private fun ScreenContent(
    isLiked: Boolean,
    model: MovieDetail,
    scrollBehavior: TopAppBarScrollBehavior,
    onEvent: (MovieDetailUiEvents) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val snackBarState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackBarState) },
        topBar = {
            TopBarItem(
                title = null,
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(MovieDetailUiEvents.NavigateBack) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Navigate back."
                        )
                    }
                },
                actions = {
                    if (model.homePage.isNotEmpty() || model.homePage.isNotBlank()) {
                        IconButton(
                            onClick = {
                                onEvent(MovieDetailUiEvents.ShareIntent(model.homePage))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share movie."
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            if (isLiked) {
                                onEvent(
                                    MovieDetailUiEvents.RemoveToFavorites(
                                        remove = model.copy(favorite = false)
                                    )
                                )
                                scope.launch {
                                    snackBarState.showSnackbar(
                                        message = "Removed from favorites.",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            } else {
                                onEvent(
                                    MovieDetailUiEvents.AddToFavorites(
                                        add = model.copy(favorite = true)
                                    )
                                )
                                scope.launch {
                                    snackBarState.showSnackbar(
                                        message = "Added to favorites.",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = null,
                            tint = if (isLiked) Color.Red else Color.DarkGray
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = lazyListState,
            contentPadding = innerPadding,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                PosterWithTextItem(
                    backdropPath = model.backdropPath,
                    title = model.title,
                    tagline = model.tagline
                )
            }
            item {
                GenresItem(
                    genres = model.genres
                )
            }
            item {
                OverviewItem(
                    overview = model.overview
                )
            }
            item {
                OtherInformationItem(
                    status = model.status,
                    releaseDate = model.releaseDate,
                    runtime = model.runtime,
                    voteAverage = model.voteAverage
                )
            }
            item {
                ProductionCompaniesItem(
                    companies = model.productionCompanies
                )
            }
            item {
                BelongsToCollectionItem(
                    name = model.belongsToCollection.name,
                    posterPath = model.belongsToCollection.posterPath
                )
            }
        }
    }
}