package com.maxidev.moviehub.feature.collection.presentation

import com.maxidev.moviehub.feature.collection.domain.model.Collection

data class CollectionsState(
    val collections: Collection = Collection()
)