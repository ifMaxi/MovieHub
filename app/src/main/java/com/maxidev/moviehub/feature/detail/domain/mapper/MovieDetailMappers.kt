package com.maxidev.moviehub.feature.detail.domain.mapper

import com.maxidev.moviehub.common.data.local.entity.BelongsToCollectionEntity
import com.maxidev.moviehub.common.data.local.entity.MovieEntity
import com.maxidev.moviehub.common.data.remote.dto.MovieDetailDto
import com.maxidev.moviehub.feature.detail.domain.model.BelongsToCollectionDomain
import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail

fun MovieDetailDto.asExternal() =
    this.let { data ->
        MovieDetail(
            id = data.id ?: 0,
            title = data.title.orEmpty(),
            posterPath = data.posterPath.orEmpty(),
            backdropPath = data.backdropPath.orEmpty(),
            overview = data.overview.orEmpty(),
            voteAverage = data.voteAverage ?: 0.0,
            tagline = data.tagline.orEmpty(),
            releaseDate = data.releaseDate.orEmpty(),
            status = data.status.orEmpty(),
            runtime = data.runtime ?: 0,
            genres = data.genres?.map { it?.name.orEmpty() }.orEmpty(),
            productionCompanies = data.productionCompanies?.map { it?.name.orEmpty() }.orEmpty(),
            belongsToCollection = BelongsToCollectionDomain(
                id = data.belongsToCollection?.id ?: 0,
                name = data.belongsToCollection?.name.orEmpty(),
                posterPath = data.belongsToCollection?.posterPath.orEmpty()
            ),
            favorite = data.favorite ?: false,
            homePage = data.homePage.orEmpty()
        )
    }

fun MovieDetail.asEntity() =
    MovieEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview,
        voteAverage = voteAverage,
        tagline = tagline,
        releaseDate = releaseDate,
        status = status,
        runtime = runtime,
        genres = genres,
        productionCompanies = productionCompanies,
        belongsToCollection = BelongsToCollectionEntity(
            collectionId = belongsToCollection.id,
            name = belongsToCollection.name,
            collectionPosterPath = belongsToCollection.posterPath
        ),
        favorite = favorite,
        homePage = homePage
    )

fun MovieEntity.asExternal() =
    MovieDetail(
        id = id,
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview,
        voteAverage = voteAverage,
        tagline = tagline,
        releaseDate = releaseDate,
        status = status,
        runtime = runtime,
        genres = genres,
        productionCompanies = productionCompanies,
        belongsToCollection = BelongsToCollectionDomain(
            id = belongsToCollection.collectionId,
            name = belongsToCollection.name,
            posterPath = belongsToCollection.collectionPosterPath
        ),
        favorite = favorite,
        homePage = homePage
    )