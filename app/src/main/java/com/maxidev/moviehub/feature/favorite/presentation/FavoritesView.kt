@file:OptIn(ExperimentalMaterial3Api::class)

package com.maxidev.moviehub.feature.favorite.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxidev.moviehub.R
import com.maxidev.moviehub.feature.components.ImageItem
import com.maxidev.moviehub.feature.components.TopBarItem
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail

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

    FavoritesScreenContent(
        favorites = state.favorites,
        scrollBehavior = scrollBehavior,
        onEvent = { event ->
            when(event) {
                FavoritesUiEvents.DeleteAllFavorites -> { viewModel.deleteAllFavorites() }
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
                    IconButton(onClick = { onEvent(FavoritesUiEvents.DeleteAllFavorites) }) {
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
            modifier = Modifier.padding(horizontal = 10.dp),
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
                ImageItem(
                    modifier = Modifier
                        .height(200.dp)
                        .aspectRatio(2f / 3f),
                    imageUrl = movie.posterPath,
                    contentScale = ContentScale.FillBounds,
                    navigateToDetail = { /* Do nothing. */ }
                )
            }
        }
    }
}

// TODO: Add alert dialog to delete all favorites.