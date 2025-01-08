package com.maxidev.moviehub.feature.detail.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.maxidev.moviehub.common.data.remote.ApiService
import com.maxidev.moviehub.common.utils.Constants.INITIAL_LOAD_SIZE
import com.maxidev.moviehub.common.utils.Constants.PAGE_SIZE
import com.maxidev.moviehub.feature.detail.data.paging.MovieCastingPagingSource
import com.maxidev.moviehub.feature.detail.data.paging.MovieImagePagingSource
import com.maxidev.moviehub.feature.detail.domain.mapper.asExternal
import com.maxidev.moviehub.feature.detail.domain.model.Casting
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail
import com.maxidev.moviehub.feature.detail.domain.model.MovieImage
import com.maxidev.moviehub.feature.detail.domain.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): MovieDetailRepository {

    override suspend fun fetchMovieDetail(movieId: Int): MovieDetail =
        withContext(Dispatchers.IO) {
            apiService.getMovieDetail(movieId)
                .asExternal()
        }

    override fun fetchMovieImage(movieId: Int): Flow<PagingData<MovieImage>> {
        val sourceFactory = { MovieImagePagingSource(apiService = apiService, movieId = movieId) }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = sourceFactory
        ).flow
            .map { pagingData ->
                val uniqueImage = mutableSetOf<String>()

                pagingData.filter { image ->
                    if (uniqueImage.contains(image.backdrops)) false else {
                        uniqueImage.add(image.backdrops)
                    }
                }
            }
    }

    override fun fetchMovieCasting(movieId: Int): Flow<PagingData<Casting>> {
        val sourceFactory = { MovieCastingPagingSource(apiService = apiService, movieId = movieId) }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = sourceFactory
        ).flow
            .map { pagingData ->
                val uniqueImage = mutableSetOf<String>()

                pagingData.filter { cast ->
                    if (uniqueImage.contains(cast.profilePath)) false else {
                        uniqueImage.add(cast.profilePath)
                    }
                }
            }
    }
}