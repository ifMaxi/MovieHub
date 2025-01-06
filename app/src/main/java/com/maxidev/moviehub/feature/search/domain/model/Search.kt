package com.maxidev.moviehub.feature.search.domain.model

data class Search(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Double
)