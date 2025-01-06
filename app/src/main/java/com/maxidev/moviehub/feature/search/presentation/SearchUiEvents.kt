package com.maxidev.moviehub.feature.search.presentation

sealed interface SearchUiEvents {

    data class NavigateToDetail(val id: Int): SearchUiEvents
}