package com.maxidev.moviehub.feature.detail.domain.repository

import com.maxidev.moviehub.feature.detail.domain.model.Casting
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail
import com.maxidev.moviehub.feature.detail.domain.model.MovieImage

interface MovieDetailRepository {

    suspend fun fetchMovieDetail(movieId: Int): MovieDetail

    suspend fun fetchMovieCasting(movieId: Int): List<Casting>

    suspend fun fetchMovieImages(movieId: Int): List<MovieImage>
}