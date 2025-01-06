package com.maxidev.moviehub.feature.search.presentation

import androidx.paging.PagingData
import com.maxidev.moviehub.feature.search.domain.model.Search
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchState(
    val search: Flow<PagingData<Search>> = emptyFlow(),
    val input: String = "",
    val isExpanded: Boolean = false,
    val onInputChange: (String) -> Unit = {},
    val onExpandedChange: (Boolean) -> Unit = {},
    val onSearch: (String) -> Unit = {}
)