@file:OptIn(ExperimentalMaterial3Api::class)

package com.maxidev.moviehub.feature.detail.presentation

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.maxidev.moviehub.R
import com.maxidev.moviehub.feature.components.TopBarItem
import com.maxidev.moviehub.feature.detail.presentation.components.BackgroundImagesItem
import com.maxidev.moviehub.feature.detail.presentation.components.BelongsToCollectionItem
import com.maxidev.moviehub.feature.detail.presentation.components.CastingContent
import com.maxidev.moviehub.feature.detail.presentation.components.GenresItem
import com.maxidev.moviehub.feature.detail.presentation.components.OtherInformationItem
import com.maxidev.moviehub.feature.detail.presentation.components.OverviewItem
import com.maxidev.moviehub.feature.detail.presentation.components.PosterWithTextItem
import com.maxidev.moviehub.feature.detail.presentation.components.ProductionCompaniesItem
import com.maxidev.moviehub.feature.navigation.NavDestinations
import kotlinx.coroutines.launch

/**
 * Composable function that displays the detailed information of a specific movie.
 *
 * This function fetches and displays the movie's details, cast, and images. It also handles user interactions
 * like adding/removing the movie from favorites, sharing the movie's homepage, and navigating back or to other screens.
 *
 * @param id The ID of the movie to display details for.
 * @param viewModel The [MovieDetailViewModel] instance responsible for fetching and managing the movie's data.
 *                  Defaults to a new instance provided by Hilt.
 * @param navController The [NavController] instance for handling navigation within the app.
 *
 * Functionality:
 * - **Data Fetching:** Fetches movie details, images, and casting information using the provided `viewModel`.
 * - **State Management:** Observes the `detailState` and `isFavorite` states from the `viewModel` to update the UI.
 * - **Favorite Handling:** Allows users to add or remove the movie from their favorites list.
 * - **Sharing:** Provides functionality to share the movie's homepage via an intent.
 * - **Navigation:** Allows navigation back to the previous screen or to a collection screen.
 * - **UI Rendering:** Uses the [ScreenContent] composable to render the movie details based on the fetched data and user interactions.
 *
 * Side Effects:
 * - **LaunchedEffect:** Fetches movie details, images, and casting when the `id` changes.
 * - **collectAsStateWithLifecycle**: Collect data safely according to lifecycle.
 * - **LocalContext**: Provides current composable context.
 *
 */
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
        putExtra(Intent.EXTRA_TEXT, state.movieDetail.homePage)
        type = "text/plain"
    }
    val chooser = Intent.createChooser(sendIntent, "Share")
    val context = LocalContext.current

    LaunchedEffect(Int) {
        viewModel.fetchMovieDetail(id)
        viewModel.fetchMovieImage(id)
        viewModel.fetchMovieCasting(id)
    }

    ScreenContent(
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
                    context.startActivity(chooser)
                }
                is MovieDetailUiEvents.NavigateToCollection -> {
                    navController.navigate(NavDestinations.CollectionScreen(events.collectionId))
                }
            }
        }
    )
}

/**
 * Composable function that displays the detailed content of a movie.
 *
 * This function structures the UI for the movie detail screen, including
 * the top app bar, movie information, and interactive elements. It handles
 * user interactions such as navigating back, sharing the movie's homepage,
 * and adding/removing the movie from favorites.
 *
 * @param isLiked Boolean indicating whether the movie is currently in the user's favorites.
 * @param state The current state of the movie detail, encapsulated in a [MovieDetailState] object.
 *              This includes details like the movie's title, overview, casting, images, etc.
 * @param scrollBehavior A [TopAppBarScrollBehavior] object that manages the scroll behavior of the top app bar.
 * @param onEvent A callback function triggered by UI events. It accepts a [MovieDetailUiEvents] object
 *                representing the event, allowing the caller to handle actions like navigation, sharing,
 *                and managing */
@Composable
private fun ScreenContent(
    isLiked: Boolean,
    state: MovieDetailState,
    scrollBehavior: TopAppBarScrollBehavior,
    onEvent: (MovieDetailUiEvents) -> Unit
) {
    val detail = state.movieDetail
    val images = state.movieImage
    val casting = state.movieCasting
    val verticalScroll = rememberScrollState()
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
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                actions = {
                    if (detail.homePage.isNotEmpty() || detail.homePage.isNotBlank()) {
                        IconButton(
                            onClick = {
                                onEvent(MovieDetailUiEvents.ShareIntent(detail.homePage))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = stringResource(R.string.share)
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            if (isLiked) {
                                onEvent(
                                    MovieDetailUiEvents.RemoveToFavorites(
                                        remove = state.movieDetail.copy(favorite = false)
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
                                        add = state.movieDetail.copy(favorite = true)
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(verticalScroll),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PosterWithTextItem(
                backdropPath = detail.backdropPath,
                title = detail.title,
                tagline = detail.tagline
            )
            GenresItem(
                genres = detail.genres
            )
            OverviewItem(
                overview = detail.overview
            )
            CastingContent(
                cast = casting
            )
            OtherInformationItem(
                status = detail.status,
                releaseDate = detail.releaseDate,
                runtime = detail.runtime,
                voteAverage = detail.voteAverage
            )
            ProductionCompaniesItem(
                companies = detail.productionCompanies
            )
            BackgroundImagesItem(
                images = images
            )
            BelongsToCollectionItem(
                id = detail.belongsToCollection.id,
                name = detail.belongsToCollection.name,
                posterPath = detail.belongsToCollection.posterPath,
                collectionId = {
                    onEvent(MovieDetailUiEvents.NavigateToCollection(detail.belongsToCollection.id))
                }
            )
        }
    }
}