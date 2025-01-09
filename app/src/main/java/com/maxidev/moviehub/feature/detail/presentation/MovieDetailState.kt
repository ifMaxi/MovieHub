package com.maxidev.moviehub.feature.detail.presentation

import com.maxidev.moviehub.feature.detail.domain.model.Casting
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail
import com.maxidev.moviehub.feature.detail.domain.model.MovieImage

data class MovieDetailState(
    val movieDetail: MovieDetail = MovieDetail(),
    val movieCasting: List<Casting> = emptyList(),
    val movieImage: List<MovieImage> = emptyList()
)