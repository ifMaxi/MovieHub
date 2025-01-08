package com.maxidev.moviehub.feature.detail.domain.model

data class MovieDetail(
    val id: Int = 0,
    val title: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val overview: String = "",
    val voteAverage: Double = 0.0,
    val tagline: String = "",
    val releaseDate: String = "",
    val status: String = "",
    val runtime: Int = 0,
    val genres: List<String> = emptyList(),
    val productionCompanies: List<String> = emptyList(),
    val belongsToCollection: BelongsToCollectionDomain = BelongsToCollectionDomain(),
    val favorite: Boolean = false,
    val homePage: String = ""
)

data class BelongsToCollectionDomain(
    val id: Int = 0,
    val name: String = "",
    val posterPath: String = ""
)