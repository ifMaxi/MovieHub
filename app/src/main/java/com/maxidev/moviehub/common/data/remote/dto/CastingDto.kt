package com.maxidev.moviehub.common.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastingDto(
    val cast: List<Cast?>? = listOf()
) {
    @Serializable
    data class Cast(
        val id: Int? = 0,
        @SerialName("known_for_department")
        val knownForDepartment: String? = "Acting",
        val name: String? = "",
        @SerialName("profile_path")
        val profilePath: String? = "",
        val character: String? = "",
    )
}