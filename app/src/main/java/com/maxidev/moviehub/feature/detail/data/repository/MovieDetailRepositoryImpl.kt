package com.maxidev.moviehub.feature.detail.data.repository

import com.maxidev.moviehub.common.data.remote.ApiService
import com.maxidev.moviehub.feature.detail.domain.mapper.asExternal
import com.maxidev.moviehub.feature.detail.domain.model.Casting
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail
import com.maxidev.moviehub.feature.detail.domain.model.MovieImage
import com.maxidev.moviehub.feature.detail.domain.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
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

    override suspend fun fetchMovieCasting(movieId: Int): List<Casting> =
        withContext(Dispatchers.IO) {
            apiService.getCredits(movieId).asExternal() ?: emptyList()
        }

    override suspend fun fetchMovieImages(movieId: Int): List<MovieImage> =
        withContext(Dispatchers.IO) {
            apiService.getImages(movieId).asExternal() ?: emptyList()
        }
}