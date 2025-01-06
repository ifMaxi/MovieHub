package com.maxidev.moviehub.common.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    @SerialName("backdrop_path")
    val backdropPath: String? = "",
    @SerialName("poster_path")
    val posterPath: String? = "",
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = BelongsToCollection(),
    val genres: List<Genre?>? = listOf(),
    val id: Int? = 0,
    val overview: String? = "",
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany?>? = listOf(),
    @SerialName("release_date")
    val releaseDate: String? = "",
    val runtime: Int? = 0,
    val status: String? = "",
    val tagline: String? = "",
    val title: String? = "",
    @SerialName("vote_average")
    val voteAverage: Double? = 0.0,
    val favorite: Boolean? = false,
    @SerialName("homepage")
    val homePage: String? = ""
) {
    @Serializable
    data class BelongsToCollection(
        val id: Int? = 0,
        val name: String? = "",
        @SerialName("poster_path")
        val posterPath: String? = ""
    )

    @Serializable
    data class Genre(
        val name: String? = ""
    )

    @Serializable
    data class ProductionCompany(
        val name: String? = ""
    )
}