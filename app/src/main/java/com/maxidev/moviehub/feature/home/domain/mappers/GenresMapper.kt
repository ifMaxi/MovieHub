package com.maxidev.moviehub.feature.home.domain.mappers

import com.maxidev.moviehub.common.data.remote.dto.GenresDto
import com.maxidev.moviehub.feature.home.domain.model.Genres

fun GenresDto.asExternal() =
    this.genres?.map { data ->
        Genres(
            id = data?.id ?: 0,
            name = data?.name.orEmpty()
        )
    }