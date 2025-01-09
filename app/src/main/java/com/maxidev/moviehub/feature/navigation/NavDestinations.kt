package com.maxidev.moviehub.feature.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

/**
 * Sealed interface representing the different destinations in the navigation graph.
 * Each data object/class represents a screen in the application.
 */
sealed interface NavDestinations {
    @Serializable data object MoviesScreen: NavDestinations
    @Serializable data object SearchScreen: NavDestinations
    @Serializable data object FavoriteScreen: NavDestinations
    @Serializable data class DetailMovieScreen(val id: Int): NavDestinations
    @Serializable data class CollectionScreen(val id: Int): NavDestinations
}

/**
 * Sealed class representing the destinations available in the application's navigation bar.
 *
 * Each destination is defined as a data object extending this class and provides:
 * - [route]: The navigation route associated with the destination. It uses the [NavDestinations] enum.
 * - [icon]: The [ImageVector] to be displayed as the icon for this destination in the navigation bar.
 * - [title]: The string title to be displayed as the label for this destination in the navigation bar.
 *
 * The [companion object] provides a list of all available destinations for easy iteration and access.
 */
sealed class NavBarDestinations(
    val route: NavDestinations,
    val icon: ImageVector,
    val title: String
) {
    data object MovieScreen: NavBarDestinations(
        route = NavDestinations.MoviesScreen,
        icon = Icons.Default.Movie,
        title = "Movies"
    )
    data object SearchScreen: NavBarDestinations(
        route = NavDestinations.SearchScreen,
        icon = Icons.Default.Search,
        title = "Search"
    )
    data object FavoritesScreen: NavBarDestinations(
        route = NavDestinations.FavoriteScreen,
        icon = Icons.Default.Star,
        title = "Favorites"
    )

    companion object {
        val destinations = listOf(
            MovieScreen,
            SearchScreen,
            FavoritesScreen
        )
    }
}