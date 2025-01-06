package com.maxidev.moviehub.common.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val overview: String,
    val voteAverage: Double,
    val tagline: String,
    val releaseDate: String,
    val status: String,
    val runtime: Int,
    val genres: List<String>,
    val productionCompanies: List<String>,
    @Embedded val belongsToCollection: BelongsToCollectionEntity,
    val favorite: Boolean,
    val homePage: String
)

data class BelongsToCollectionEntity(
    val collectionId: Int,
    val name: String,
    val collectionPosterPath: String
)