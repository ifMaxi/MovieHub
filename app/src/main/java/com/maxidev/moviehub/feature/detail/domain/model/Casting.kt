package com.maxidev.moviehub.feature.detail.domain.model

data class Casting(
    val id: Int,
    val name: String,
    val profilePath: String,
    val character: String,
    val knownForDepartment: String
)