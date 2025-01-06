package com.maxidev.moviehub.feature.detail.domain.repository

import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail

interface MovieDetailRepository {

    suspend fun fetchMovieDetail(movieId: Int): MovieDetail
}