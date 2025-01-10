@file:OptIn(ExperimentalMaterial3Api::class)

package com.maxidev.moviehub.feature.favorite.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.maxidev.moviehub.R
import com.maxidev.moviehub.feature.components.TopBarItem
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail
import com.maxidev.moviehub.feature.favorite.presentation.components.DialogItem

/**
 * Displays the user's favorite items.
 *
 * This composable function is responsible for rendering the UI that shows a list of the user's
 * favorite items. It utilizes a [FavoritesViewModel] to manage the state of the favorites and
 * handles user interactions such as deleting all favorites.
 *
 * @param viewModel The [FavoritesViewModel] instance used to manage the favorites data and state.
 *                  Defaults to a new [FavoritesViewModel] instance obtained via Hilt's
 *                  [hiltViewModel] if not provided.
 *
 * @OptIn ExperimentalMaterial3Api: This function uses experimental Material 3 APIs.
 */
@Composable
fun FavoritesView(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.favoriteState.collectAsStateWithLifecycle()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topBarState)
    var openDialog by remember { mutableStateOf(false) }

    FavoritesScreenContent(
        openDialog = openDialog,
        favorites = state.favorites,
        scrollBehavior = scrollBehavior,
        onEvent = { event ->
            when(event) {
                FavoritesUiEvents.DeleteAllFavorites -> { viewModel.deleteAllFavorites() }
                is FavoritesUiEvents.DismissDialog -> { openDialog = false }
                is FavoritesUiEvents.OpenDialog -> { openDialog = true }
            }
        }
    )
}

/**
 * Composable function that displays the content of the Favorites screen.
 *
 * This screen shows a grid of favorited movies, allowing the user to view
 * their saved movies and delete all favorites at once.
 *
 * @param favorites A list of [MovieDetail] objects representing the user's favorited movies.
 * @param scrollBehavior The [TopAppBarScrollBehavior] to handle scrolling behavior of the top app bar.
 * @param onEvent A callback function to handle UI events, such as deleting all favorites.
 *                It takes a [FavoritesUiEvents] object as a parameter.
 */
@Composable
private fun FavoritesScreenContent(
    openDialog: Boolean,
    favorites: List<MovieDetail>,
    scrollBehavior: TopAppBarScrollBehavior,
    onEvent: (FavoritesUiEvents) -> Unit
) {
    val lazyGridState = rememberLazyGridState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarItem(
                title = R.string.favorites,
                navigationIcon = {},
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(
                                FavoritesUiEvents.OpenDialog(openDialog = openDialog)
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete_all_favorites)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            contentPadding = innerPadding,
            state = lazyGridState,
            columns = GridCells.Adaptive(120.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = favorites,
                key = { key -> key.id },
                contentType = { type -> type::class }
            ) { movie ->
                val posterLink = "https://image.tmdb.org/t/p/original${movie.posterPath}"

                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .aspectRatio(2f / 3f)
                        .clip(RoundedCornerShape(4)),
                    model = posterLink,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }

    if (openDialog) {
        DialogItem(
            onDismiss = {
                onEvent(FavoritesUiEvents.DismissDialog(onDismiss = true))
            },
            onConfirmation = {
                onEvent(FavoritesUiEvents.DeleteAllFavorites)
                onEvent(FavoritesUiEvents.DismissDialog(onDismiss = true))
            },
            title = R.string.wait_alert,
            text = R.string.delete_all_alert
        )
    }
}