package com.maxidev.moviehub.feature.search.domain.mapper

import com.maxidev.moviehub.common.data.remote.dto.SearchDto
import com.maxidev.moviehub.feature.search.domain.model.Search

fun SearchDto.asExternal() =
    this.results?.map { data ->
        Search(
            id = data?.id ?: 0,
            title = data?.title.orEmpty(),
            overview = data?.overview.orEmpty(),
            posterPath = data?.posterPath.orEmpty(),
            voteAverage = data?.voteAverage ?: 0.0
        )
    }