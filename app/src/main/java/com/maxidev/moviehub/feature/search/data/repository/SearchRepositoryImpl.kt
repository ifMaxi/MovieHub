package com.maxidev.moviehub.feature.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.maxidev.moviehub.common.data.remote.ApiService
import com.maxidev.moviehub.feature.search.data.paging.SearchPagingSource
import com.maxidev.moviehub.feature.search.domain.model.Search
import com.maxidev.moviehub.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): SearchRepository {

    override fun fetchSearch(query: String): Flow<PagingData<Search>> {
        val factory = { SearchPagingSource(query = query, apiService = apiService) }

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = factory
        ).flow
    }
}