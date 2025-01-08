package com.maxidev.moviehub.feature.detail.domain.mapper

import com.maxidev.moviehub.common.data.remote.dto.MovieImageDto
import com.maxidev.moviehub.feature.detail.domain.model.MovieImage

fun MovieImageDto.asExternal() =
    this.backdrops?.map { data ->
        MovieImage(
            backdrops = data?.filePath.orEmpty()
        )
    }