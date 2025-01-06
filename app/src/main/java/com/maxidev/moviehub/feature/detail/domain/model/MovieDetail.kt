package com.maxidev.moviehub.feature.detail.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val overview: String,
    val voteAverage: Double,
    val tagline: String,
    val releaseDate: String,
    val status: String,
    val runtime: Int,
    val genres: List<String>,
    val productionCompanies: List<String>,
    val belongsToCollection: BelongsToCollectionDomain,
    val favorite: Boolean,
    val homePage: String
)

data class BelongsToCollectionDomain(
    val id: Int,
    val name: String,
    val posterPath: String
)