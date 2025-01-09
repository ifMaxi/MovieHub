package com.maxidev.moviehub.feature.collection.domain.repository

import com.maxidev.moviehub.feature.collection.domain.model.Collection

interface CollectionRepository {

    suspend fun fetchCollection(collectionId: Int): Collection
}