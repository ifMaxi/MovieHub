package com.maxidev.moviehub.common.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieImageDto(
    val backdrops: List<Backdrop?>? = listOf()
) {
    @Serializable
    data class Backdrop(
        @SerialName("file_path")
        val filePath: String? = ""
    )
}