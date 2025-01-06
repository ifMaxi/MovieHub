package com.maxidev.moviehub.feature.search.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maxidev.moviehub.feature.search.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    private val _input = mutableStateOf("")
    val input = _input

    fun onInputChanged(value: String) {
        _input.value = value
    }

    fun clearText() { _input.value = "" }

    fun getSearchResults(query: String) {
        _searchState.update { search ->
            search.copy(
                search = repository.fetchSearch(query)
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
            )
        }
    }
}