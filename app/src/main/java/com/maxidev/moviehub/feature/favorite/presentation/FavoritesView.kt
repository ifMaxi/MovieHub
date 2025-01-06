@file:OptIn(ExperimentalMaterial3Api::class)

package com.maxidev.moviehub.feature.favorite.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxidev.moviehub.feature.components.ImageItem
import com.maxidev.moviehub.feature.components.TopBarItem
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail

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
                title = "Favorites",
                navigationIcon = {},
                actions = {
                    IconButton(onClick = { onEvent(FavoritesUiEvents.DeleteAllFavorites) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete all favorites"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            contentPadding = innerPadding,
            state = lazyGridState,
            columns = GridCells.Adaptive(120.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = favorites
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