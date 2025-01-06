package com.maxidev.moviehub.feature.home.domain.mappers

import com.maxidev.moviehub.common.data.remote.dto.MoviesDto
import com.maxidev.moviehub.feature.home.domain.model.Movies

fun MoviesDto.asExternal() =
    this.results?.map { data ->
        Movies(
            id = data?.id ?: 0,
            posterPath = data?.posterPath.orEmpty(),
            backdropPath = data?.backdropPath.orEmpty(),
            title = data?.title.orEmpty()
        )
    }