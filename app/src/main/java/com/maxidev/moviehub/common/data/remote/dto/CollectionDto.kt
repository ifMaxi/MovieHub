package com.maxidev.moviehub.common.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionDto(
    val name: String? = "",
    val overview: String? = "",
    @SerialName("poster_path")
    val posterPath: String? = "",
    val parts: List<Part?>? = listOf()
) {
    @Serializable
    data class Part(
        val id: Int? = 0,
        val title: String? = "",
        val overview: String? = "",
        @SerialName("poster_path")
        val posterPath: String? = "",
        @SerialName("release_date")
        val releaseDate: String? = "",
        @SerialName("vote_average")
        val voteAverage: Double? = 0.0
    )
}