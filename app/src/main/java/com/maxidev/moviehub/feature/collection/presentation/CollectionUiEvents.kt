package com.maxidev.moviehub.feature.collection.presentation

sealed interface CollectionUiEvents {
    data object NavigateBack: CollectionUiEvents
    data object NavigateToHome: CollectionUiEvents
    data class NavigateToDetail(val id: Int): CollectionUiEvents
}