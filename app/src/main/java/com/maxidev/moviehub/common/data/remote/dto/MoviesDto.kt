package com.maxidev.moviehub.common.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesDto(
    val page: Int? = 0,
    val results: List<Result?>? = listOf()
) {
    @Serializable
    data class Result(
        val id: Int? = 0,
        @SerialName("poster_path")
        val posterPath: String? = "",
        @SerialName("backdrop_path")
        val backdropPath: String? = "",
        val title: String? = ""
    )
}