package com.maxidev.moviehub.common.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenresDto(
    val genres: List<Genre?>? = listOf()
) {
    @Serializable
    data class Genre(
        val id: Int? = 0,
        val name: String? = ""
    )
}