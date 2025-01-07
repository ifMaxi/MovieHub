package com.maxidev.moviehub.feature.home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.maxidev.moviehub.common.data.remote.ApiService
import com.maxidev.moviehub.common.utils.Constants.INITIAL_LOAD_SIZE
import com.maxidev.moviehub.common.utils.Constants.PAGE_SIZE
import com.maxidev.moviehub.feature.home.data.paging.NowPlayingMoviesPagingSource
import com.maxidev.moviehub.feature.home.data.paging.PopularMoviesPagingSource
import com.maxidev.moviehub.feature.home.data.paging.TopRatedMoviesPagingSource
import com.maxidev.moviehub.feature.home.data.paging.TrendingMoviesPagingSource
import com.maxidev.moviehub.feature.home.data.paging.UpcomingPagingSource
import com.maxidev.moviehub.feature.home.domain.mappers.asExternal
import com.maxidev.moviehub.feature.home.domain.model.Genres
import com.maxidev.moviehub.feature.home.domain.model.Movies
import com.maxidev.moviehub.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): HomeRepository {

    override fun fetchTrendingMovies(): Flow<PagingData<Movies>> {

        val sourceFactory = { TrendingMoviesPagingSource(apiService) }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = sourceFactory
        ).flow
    }

    override fun fetchNowPlayingMovies(): Flow<PagingData<Movies>> {
        val sourceFactory = { NowPlayingMoviesPagingSource(apiService) }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = sourceFactory
        ).flow
    }

    override fun fetchPopularMovies(): Flow<PagingData<Movies>> {
        val sourceFactory = { PopularMoviesPagingSource(apiService) }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = sourceFactory
        ).flow
    }

    override fun fetchUpcomingMovies(): Flow<PagingData<Movies>> {
        val sourceFactory = { UpcomingPagingSource(apiService) }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = sourceFactory
        ).flow
    }

    override fun fetchTopRatedMovies(): Flow<PagingData<Movies>> {
        val sourceFactory = { TopRatedMoviesPagingSource(apiService) }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = sourceFactory
        ).flow
    }

    override suspend fun fetchGenres(): List<Genres> =
        withContext(Dispatchers.IO) {
            apiService.getGenres().asExternal() ?: emptyList()
        }
}