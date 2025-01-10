package com.maxidev.moviehub.feature.favorite.presentation

sealed interface FavoritesUiEvents {
    data object DeleteAllFavorites : FavoritesUiEvents
    data class DismissDialog(val onDismiss: Boolean) : FavoritesUiEvents
    data class OpenDialog(val openDialog: Boolean) : FavoritesUiEvents
}