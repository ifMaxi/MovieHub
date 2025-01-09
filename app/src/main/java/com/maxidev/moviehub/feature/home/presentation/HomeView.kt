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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.stringResource
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
import com.maxidev.moviehub.R
import com.maxidev.moviehub.common.presentation.theme.nunitoFont
import com.maxidev.moviehub.feature.components.HeaderItem
import com.maxidev.moviehub.feature.components.ImageItem
import com.maxidev.moviehub.feature.components.PagedRow
import com.maxidev.moviehub.feature.components.TopBarItem
import com.maxidev.moviehub.feature.home.domain.model.Genres
import com.maxidev.moviehub.feature.home.presentation.components.GenreItem
import com.maxidev.moviehub.feature.navigation.NavDestinations
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.absoluteValue

/**
 * [HomeView] is a composable function that represents the main screen of the application,
 * displaying a list of movies and providing navigation to the detail screen.
 *
 * @param navController The navigation controller used for navigating to different screens.
 * @param viewModel The [HomeViewModel] instance responsible for managing the state and logic of the home screen.
 */
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

/**
 * Displays the main content of the home screen, including movie lists and genre information.
 *
 * This composable function renders the screen's content within a [Scaffold] and utilizes
 * various components to display movie data fetched from different sources. It also handles
 * pull-to-refresh functionality and vertical scrolling.
 *
 * @param state The [HomeState] containing the data to be displayed, such as trending,
 * now playing, popular, top-rated, and upcoming movies, as well as genre information.
 * @param scrollBehavior The [TopAppBarScrollBehavior] for managing the behavior of the top app bar,
 * allowing it to react to scroll events.
 * @param onEvent A lambda function that handles user interactions with the UI, such as pull-to-refresh
 * and navigating to the movie detail screen. It takes a [HomeUiEvents] as a parameter.
 */
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
    val genresState = state.genres
    val rememberPullToRefreshState = rememberPullToRefreshState()
    val verticalScrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarItem(
                title = R.string.app_name,
                navigationIcon = { /* Do nothing. */ },
                actions = { /* Do nothing. */ },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = false,
            onRefresh = { onEvent(HomeUiEvents.OnPullToRefresh) },
            state = rememberPullToRefreshState
        ) {
            // Column is used instead of LazyColumn to avoid long GC "freezes".
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(verticalScrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderItem(
                    header = R.string.genres,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 10.dp, vertical = 16.dp)
                )
                GenresContent(
                    genres = genresState
                )
                HeaderItem(
                    header = R.string.now_playing,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 10.dp, vertical = 16.dp)
                )
                PagerRowItem(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    items = nowPlayingMoviesState,
                    key = { key -> key.id },
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
                HeaderItem(
                    header = R.string.trending_today,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 10.dp, vertical = 16.dp)
                )
                PagedRow(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    items = trendingMoviesState,
                    key = { key -> key.id },
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
                HeaderItem(
                    header = R.string.popular,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 10.dp, vertical = 16.dp)
                )
                PagedRow(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    items = popularMoviesState,
                    key = { key -> key.id },
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
                HeaderItem(
                    header = R.string.top_rated,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 10.dp, vertical = 16.dp)
                )
                PagedRow(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    items = topRatedMoviesState,
                    key = { key -> key.id },
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
                HeaderItem(
                    header = R.string.upcoming,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 10.dp, vertical = 16.dp)
                )
                PagedRow(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    items = upcomingMoviesState,
                    key = { key -> key.id },
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

/**
 * Displays a horizontal list of genre items.
 *
 * This composable renders a `LazyRow` to efficiently display a list of genres.
 * Each genre is represented by a `GenreItem` composable.
 *
 * @param modifier Modifier to be applied to the container.
 * @param genres The list of [Genres] to display. If the list is empty, nothing is displayed.
 *
 * Example Usage:
 * ```
 *  GenresContent(
 *      modifier = Modifier.padding(16.dp),
 *      genres = listOf(
 *          Genres(1, "Action"),
 *          Genres(2, "Comedy"),
 *          Genres(3, "Drama")
 *      )
 *  )
 * ```
 *
 * Notes:
 * - Uses `LazyRow` for efficient rendering of potentially long lists.
 * - Centers the content within the parent `Box`.
 * - Adds horizontal padding to the `LazyRow`.
 * - Spacing of 10.dp between genre items.
 * - each item has a unique key by the genre id.
 */
@Composable
private fun GenresContent(
    modifier: Modifier = Modifier,
    genres: List<Genres>
) {
    val lazyState = rememberLazyListState()

    if (genres.isNotEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                state = lazyState,
                contentPadding = PaddingValues(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = genres,
                    key = { key -> key.id }
                ) { genre ->
                    GenreItem(name = genre.name)
                }
            }
        }
    }
}

/**
 * A composable function that displays a horizontally scrollable pager of items fetched using
 * [LazyPagingItems]. It supports automatic scrolling, item-specific keys, and loading/error states.
 *
 * @param modifier The [Modifier] to be applied to the pager container.
 * @param items The [LazyPagingItems] containing the data to be displayed.
 * @param key A function that uniquely identifies each item in the pager. This is essential for
 *            correct recomposition and state restoration.
 * @param content A composable function that defines how each individual item should be rendered.
 *                 It takes the item */
@Composable
private fun <T: Any> PagerRowItem(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any),
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
            key = { key(items[it]!!) },
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 50.dp),
            pageSpacing = 4.dp
        ) { pager ->
            val item = items[pager]

            Card(
                elevation = CardDefaults.cardElevation(6.dp),
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
                            text = stringResource(R.string.no_data_available),
                            fontFamily = nunitoFont,
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
                                is HttpException -> { stringResource(R.string.something_wrong) }
                                is IOException -> { stringResource(R.string.internet_problem) }
                                else -> { stringResource(R.string.unknown_error) }
                            },
                            fontFamily = nunitoFont,
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
                            text = stringResource(R.string.error_occurred),
                            fontFamily = nunitoFont,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}