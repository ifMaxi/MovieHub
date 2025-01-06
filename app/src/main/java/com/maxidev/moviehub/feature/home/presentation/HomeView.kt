@file:OptIn(ExperimentalMaterial3Api::class)

package com.maxidev.moviehub.feature.home.presentation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.maxidev.moviehub.feature.components.ImageItem
import com.maxidev.moviehub.feature.components.TopBarItem
import com.maxidev.moviehub.feature.navigation.NavDestinations
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.absoluteValue

@Composable
fun HomeView(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.homeState.collectAsStateWithLifecycle()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topBarState)

    ScreenContent(
        state = state,
        scrollBehavior = scrollBehavior,
        onEvent = { event ->
            when(event) {
                is HomeUiEvents.NavigateToDetail -> {
                    navController.navigate(
                        NavDestinations.DetailMovieScreen(id = event.id)
                    )
                }
                HomeUiEvents.OnPullToRefresh -> viewModel.refreshAll()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    state: HomeState,
    scrollBehavior: TopAppBarScrollBehavior,
    onEvent: (HomeUiEvents) -> Unit
) {
    val trendingMoviesState = state.trendingMovies.collectAsLazyPagingItems()
    val nowPlayingMoviesState = state.nowPlayingMovies.collectAsLazyPagingItems()
    val popularMoviesState = state.popularMovies.collectAsLazyPagingItems()
    val topRatedMoviesState = state.topRatedMovies.collectAsLazyPagingItems()
    val upcomingMoviesState = state.upcomingMovies.collectAsLazyPagingItems()
    val rememberPullToRefreshState = rememberPullToRefreshState()
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarItem(
                title = "MovieHub",
                navigationIcon = { /* Do nothing. */ },
                actions = { /* Do nothing. */ },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = false,
            onRefresh = {
                onEvent(HomeUiEvents.OnPullToRefresh)
            },
            state = rememberPullToRefreshState
        ) {
            LazyColumn(
                state = lazyListState,
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column(
                        modifier = Modifier.fillParentMaxWidth()
                    ) {
                        Text(
                            text = "Now playing",
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        PagerRowItem(
                            items = nowPlayingMoviesState,
                            content = {
                                ImageItem(
                                    modifier = Modifier
                                        .height(300.dp)
                                        .aspectRatio(2f / 3f),
                                    imageUrl = it.posterPath,
                                    contentScale = ContentScale.FillBounds,
                                    navigateToDetail = {
                                        onEvent(HomeUiEvents.NavigateToDetail(id = it.id))
                                    }
                                )
                            }
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier.fillParentMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Trending today",
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        PagedRow(
                            items = trendingMoviesState,
                            content = {
                                ImageItem(
                                    modifier = Modifier
                                        .height(200.dp),
                                    imageUrl = it.posterPath,
                                    contentScale = ContentScale.FillBounds,
                                    navigateToDetail = {
                                        onEvent(HomeUiEvents.NavigateToDetail(id = it.id))
                                    }
                                )
                            }
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier.fillParentMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Popular",
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        PagedRow(
                            items = popularMoviesState,
                            content = {
                                ImageItem(
                                    modifier = Modifier
                                        .height(200.dp),
                                    imageUrl = it.posterPath,
                                    contentScale = ContentScale.FillBounds,
                                    navigateToDetail = {
                                        onEvent(HomeUiEvents.NavigateToDetail(id = it.id))
                                    }
                                )
                            }
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier.fillParentMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Top rated",
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        PagedRow(
                            items = topRatedMoviesState,
                            content = {
                                ImageItem(
                                    modifier = Modifier
                                        .height(200.dp),
                                    imageUrl = it.posterPath,
                                    contentScale = ContentScale.FillBounds,
                                    navigateToDetail = {
                                        onEvent(HomeUiEvents.NavigateToDetail(id = it.id))
                                    }
                                )
                            }
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier.fillParentMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Upcoming",
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        PagedRow(
                            items = upcomingMoviesState,
                            content = {
                                ImageItem(
                                    modifier = Modifier
                                        .height(200.dp),
                                    imageUrl = it.posterPath,
                                    contentScale = ContentScale.FillBounds,
                                    navigateToDetail = {
                                        onEvent(HomeUiEvents.NavigateToDetail(id = it.id))
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun <T: Any> PagerRowItem(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    content: @Composable (T) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { items.itemCount })
    val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()
    val pageInteractionSource = remember { MutableInteractionSource() }
    val pageIsPressed by pageInteractionSource.collectIsDraggedAsState()
    val autoAdvance = !pagerIsDragged && !pageIsPressed

    if (autoAdvance) {
        LaunchedEffect(pagerState, pageInteractionSource) {
            while (true) {
                try {
                    delay(5000)
                    val nextPage = (pagerState.currentPage + 1) % items.itemCount

                    pagerState.animateScrollToPage(
                        page = nextPage,
                        animationSpec = spring(
                            stiffness = Spring.StiffnessVeryLow,
                            dampingRatio = Spring.DampingRatioLowBouncy
                        )
                    )
                } catch (e: ArithmeticException) { e.message }
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 50.dp),
            pageSpacing = 4.dp
        ) { pager ->
            val item = items[pager]

            Card(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        val xOffset = (constraints.maxWidth - placeable.width) / 2

                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(IntOffset(xOffset, 0))
                        }
                    }
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - pager) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                    }
            ) {
                item?.let { cont ->
                    content(cont)
                }
            }
        }

        items.loadState.let { loadStates ->

            when {
                loadStates.refresh is LoadState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(strokeWidth = 2.dp)
                    }
                }
                loadStates.refresh is LoadState.NotLoading && items.itemCount < 1 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "No data available",
                            textAlign = TextAlign.Center
                        )
                    }
                }
                loadStates.refresh is LoadState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = when ((loadStates.refresh as LoadState.Error).error) {
                                is HttpException -> {
                                    "Oops, something went wrong!"
                                }
                                is IOException -> {
                                    "Couldn't reach server, check your internet connection!"
                                }
                                else -> {
                                    "Unknown error occurred"
                                }
                            },
                            textAlign = TextAlign.Center
                        )
                    }
                }
                loadStates.append is LoadState.Loading -> {
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
                loadStates.append is LoadState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "An error occurred",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun <T: Any> PagedRow(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    content: @Composable (T) -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(count = items.itemCount) {
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
                                    text = "No data available",
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
                                        is HttpException -> {
                                            "Oops, something went wrong!"
                                        }

                                        is IOException -> {
                                            "Couldn't reach server, check your internet connection!"
                                        }

                                        else -> {
                                            "Unknown error occurred"
                                        }
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
                                    text = "An error occurred",
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