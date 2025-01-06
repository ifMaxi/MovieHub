package com.maxidev.moviehub.feature.favorite.presentation

import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail

data class FavoriteState(
    val favorites: List<MovieDetail> = emptyList()
)