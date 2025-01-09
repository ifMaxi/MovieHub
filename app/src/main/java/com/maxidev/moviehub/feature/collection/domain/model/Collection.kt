package com.maxidev.moviehub.feature.collection.domain.model

data class Collection(
    val collectionName: String = "",
    val collectionOverview: String = "",
    val collectionPosterPath: String = "",
    val parts: List<CollectionParts> = listOf()
) {
    data class CollectionParts(
        val id: Int = 0,
        val posterPath: String = "",
        val title: String = "",
        val overview: String = "",
        val releaseDate: String = "",
        val voteAverage: Double = 0.0
    )
}