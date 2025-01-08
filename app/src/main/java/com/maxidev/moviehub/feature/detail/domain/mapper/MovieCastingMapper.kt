package com.maxidev.moviehub.feature.detail.domain.mapper

import com.maxidev.moviehub.common.data.remote.dto.CastingDto
import com.maxidev.moviehub.feature.detail.domain.model.Casting

fun CastingDto.asExternal() =
    this.cast?.map { data ->
        Casting(
            id = data?.id ?: 0,
            name = data?.name.orEmpty(),
            profilePath = data?.profilePath.orEmpty(),
            character = data?.character.orEmpty(),
            knownForDepartment = data?.knownForDepartment.orEmpty()
        )
    }