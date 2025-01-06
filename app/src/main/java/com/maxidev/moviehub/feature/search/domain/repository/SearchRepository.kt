package com.maxidev.moviehub.feature.search.domain.repository

import androidx.paging.PagingData
import com.maxidev.moviehub.feature.search.domain.model.Search
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun fetchSearch(query: String): Flow<PagingData<Search>>
}