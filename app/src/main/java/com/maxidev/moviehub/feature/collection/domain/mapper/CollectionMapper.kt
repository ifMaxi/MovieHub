package com.maxidev.moviehub.feature.collection.domain.mapper

import com.maxidev.moviehub.common.data.remote.dto.CollectionDto
import com.maxidev.moviehub.feature.collection.domain.model.Collection

fun CollectionDto.asExternal() =
    this.parts?.map { data ->
        Collection.CollectionParts(
            id = data?.id ?: 0,
            posterPath = data?.posterPath.orEmpty(),
            title = data?.title.orEmpty(),
            overview = data?.overview.orEmpty(),
            releaseDate = data?.releaseDate.orEmpty(),
            voteAverage = data?.voteAverage ?: 0.0
        )
    }?.let {
        Collection(
            collectionName = this.name.orEmpty(),
            collectionOverview = this.overview.orEmpty(),
            collectionPosterPath = this.posterPath.orEmpty(),
            parts = it
        )
    }