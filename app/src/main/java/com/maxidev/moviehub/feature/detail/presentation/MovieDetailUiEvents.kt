package com.maxidev.moviehub.feature.detail.presentation

import com.maxidev.moviehub.feature.detail.domain.model.MovieDetail

sealed interface MovieDetailUiEvents {
    data object NavigateBack : MovieDetailUiEvents
    data class NavigateToCollection(val collectionId: Int): MovieDetailUiEvents
    data class ShareIntent(val homePage: String) : MovieDetailUiEvents
    data class AddToFavorites(val add: MovieDetail): MovieDetailUiEvents
    data class RemoveToFavorites(val remove: MovieDetail): MovieDetailUiEvents
}