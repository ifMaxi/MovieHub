package com.maxidev.moviehub.feature.favorite.presentation

sealed interface FavoritesUiEvents {
    data object DeleteAllFavorites : FavoritesUiEvents
}