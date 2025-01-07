package com.maxidev.moviehub.feature.home.presentation

import androidx.paging.PagingData
import com.maxidev.moviehub.feature.home.domain.model.Genres
import com.maxidev.moviehub.feature.home.domain.model.Movies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val trendingMovies: Flow<PagingData<Movies>> = emptyFlow(),
    val nowPlayingMovies: Flow<PagingData<Movies>> = emptyFlow(),
    val popularMovies: Flow<PagingData<Movies>> = emptyFlow(),
    val topRatedMovies: Flow<PagingData<Movies>> = emptyFlow(),
    val upcomingMovies: Flow<PagingData<Movies>> = emptyFlow(),
    val genres: List<Genres> = emptyList()
)