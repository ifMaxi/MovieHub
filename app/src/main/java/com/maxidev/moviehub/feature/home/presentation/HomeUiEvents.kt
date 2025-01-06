package com.maxidev.moviehub.feature.home.presentation

sealed interface HomeUiEvents {

    data object OnPullToRefresh: HomeUiEvents
    data class NavigateToDetail(val id: Int): HomeUiEvents
}