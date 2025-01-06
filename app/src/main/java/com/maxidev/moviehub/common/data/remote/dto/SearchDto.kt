package com.maxidev.moviehub.common.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchDto(
    val page: Int? = 0,
    val results: List<Result?>? = listOf()
) {
    @Serializable
    data class Result(
        val id: Int? = 0,
        val title: String? = "",
        val overview: String? = "",
        @SerialName("poster_path")
        val posterPath: String? = "",
        @SerialName("vote_average")
        val voteAverage: Double? = 0.0,
    )
}