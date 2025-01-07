package com.maxidev.moviehub.feature.home.domain.repository

import androidx.paging.PagingData
import com.maxidev.moviehub.feature.home.domain.model.Genres
import com.maxidev.moviehub.feature.home.domain.model.Movies
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun fetchTrendingMovies(): Flow<PagingData<Movies>>

    fun fetchNowPlayingMovies(): Flow<PagingData<Movies>>

    fun fetchPopularMovies(): Flow<PagingData<Movies>>

    fun fetchUpcomingMovies(): Flow<PagingData<Movies>>

    fun fetchTopRatedMovies(): Flow<PagingData<Movies>>

    suspend fun fetchGenres(): List<Genres>
}