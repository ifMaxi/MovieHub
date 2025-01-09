@file:OptIn(ExperimentalMaterial3Api::class)

package com.maxidev.moviehub.feature.collection.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.maxidev.moviehub.R
import com.maxidev.moviehub.feature.collection.presentation.components.CollectionHeaderItem
import com.maxidev.moviehub.feature.collection.presentation.components.CollectionItem
import com.maxidev.moviehub.feature.components.TopBarItem
import com.maxidev.moviehub.feature.navigation.NavDestinations

@Composable
fun CollectionScreen(
    collectionId: Int,
    viewModel: CollectionViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.collectionState.collectAsStateWithLifecycle()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topBarState)

    LaunchedEffect(Int) {
        viewModel.fetchCollection(collectionId)
    }

    CollectionContent(
        state = state,
        scrollBehavior = scrollBehavior,
        onEvent = { events ->
            when (events) {
                CollectionUiEvents.NavigateBack -> { navController.popBackStack() }
                CollectionUiEvents.NavigateToHome -> {
                    val popUp = navController.graph.findStartDestination().id

                    navController.navigate(NavDestinations.MoviesScreen) {
                        popUpTo(popUp) { inclusive = true }
                    }
                }
                is CollectionUiEvents.NavigateToDetail -> {
                    navController.navigate(NavDestinations.DetailMovieScreen(events.id))
                }
            }
        }
    )
}

@Composable
private fun CollectionContent(
    state: CollectionsState,
    scrollBehavior: TopAppBarScrollBehavior,
    onEvent: (CollectionUiEvents) -> Unit
) {
    val collection = state.collections
    val lazyState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarItem(
                title = null,
                navigationIcon = {
                    IconButton(onClick = { onEvent(CollectionUiEvents.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onEvent(CollectionUiEvents.NavigateToHome) }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = stringResource(R.string.navigate_to_home)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier,
            state = lazyState,
            contentPadding = innerPadding,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                CollectionHeaderItem(
                    collectionName = collection.collectionName,
                    collectionOverview = collection.collectionOverview,
                    collectionPosterPath = collection.collectionPosterPath
                )
            }
            items(
                items = collection.parts,
                key = { key -> key.id }
            ) { item ->
                CollectionItem(
                    id = item.id,
                    title = item.title,
                    posterPath = item.posterPath,
                    overview = item.overview,
                    releaseDate = item.releaseDate,
                    voteAverage = item.voteAverage,
                    navigateToDetail = {
                        onEvent(CollectionUiEvents.NavigateToDetail(id = item.id))
                    }
                )
            }
        }
    }
}