package com.maxidev.moviehub.feature.home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
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
import kotlinx.coroutines.flow.map
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
            .map { pagingData ->
                val uniqueImage = mutableSetOf<String>()

                pagingData.filter { image ->
                    if (uniqueImage.contains(image.posterPath)) false else {
                        uniqueImage.add(image.posterPath)
                    }
                }
            }
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
            .map { pagingData ->
                val uniqueImage = mutableSetOf<String>()

                pagingData.filter { image ->
                    if (uniqueImage.contains(image.posterPath)) false else {
                        uniqueImage.add(image.posterPath)
                    }
                }
            }
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
            .map { pagingData ->
                val uniqueImage = mutableSetOf<String>()

                pagingData.filter { image ->
                    if (uniqueImage.contains(image.posterPath)) false else {
                        uniqueImage.add(image.posterPath)
                    }
                }
            }
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
            .map { pagingData ->
                val uniqueImage = mutableSetOf<String>()

                pagingData.filter { image ->
                    if (uniqueImage.contains(image.posterPath)) false else {
                        uniqueImage.add(image.posterPath)
                    }
                }
            }
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
            .map { pagingData ->
                val uniqueImage = mutableSetOf<String>()

                pagingData.filter { image ->
                    if (uniqueImage.contains(image.posterPath)) false else {
                        uniqueImage.add(image.posterPath)
                    }
                }
            }
    }

    override suspend fun fetchGenres(): List<Genres> =
        withContext(Dispatchers.IO) {
            apiService.getGenres().asExternal() ?: emptyList()
        }
}