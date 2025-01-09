package com.maxidev.moviehub.feature.collection.data.repository

import com.maxidev.moviehub.common.data.remote.ApiService
import com.maxidev.moviehub.feature.collection.domain.mapper.asExternal
import com.maxidev.moviehub.feature.collection.domain.model.Collection
import com.maxidev.moviehub.feature.collection.domain.repository.CollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): CollectionRepository {

    override suspend fun fetchCollection(collectionId: Int): Collection =
        withContext(Dispatchers.IO) {
            apiService.getCollectionId(collectionId).asExternal() ?: Collection()
        }
}