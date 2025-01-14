package com.maxidev.moviehub.feature.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.maxidev.moviehub.common.presentation.theme.nunitoFont
import com.maxidev.moviehub.feature.collection.presentation.CollectionScreen
import com.maxidev.moviehub.feature.detail.presentation.MovieDetailView
import com.maxidev.moviehub.feature.favorite.presentation.FavoritesView
import com.maxidev.moviehub.feature.home.presentation.HomeView
import com.maxidev.moviehub.feature.search.presentation.SearchView
import com.maxidev.moviehub.feature.settings.presentation.SettingsView

/**
 * [NavigationGraph] is the main navigation component for the application.
 * It defines the navigation structure and handles the bottom navigation bar.
 *
 * @param navHostController The [NavHostController] responsible for navigating between screens.
 * @param startDestination The initial screen to display. Defaults to [NavDestinations.MoviesScreen].
 *
 *  This Composable does the following:
 *   1. **Manages Navigation**: Uses a [NavHostController] to control navigation between different screens.
 *   2. **Bottom Navigation Bar**: Displays a [NavigationBar] at the bottom of the screen with items defined in [NavBarDestinations].
 *   3. **Navigation Bar Item Selection**: Highlights the currently selected navigation bar item based on the current route.
 *   4. **Navigation Bar Item Actions**: Handles clicks on navigation bar items, navigating to the corresponding screen while maintaining navigation state.
 *   5. **Content Area**: Uses a [Scaffold] to organize the screen, placing the [NavigationBar] at the bottom and the screen content in the main area.
 *   6. **NavHost Setup**:  Defines the [NavHost] to handle the actual composable screens, using the provided [NavHostController] and [startDestination].
 *   7. **Screen Definitions**: Specifies how each destination within [NavDestinations] should be presented:
 *       - **MoviesScreen**: Shows the [HomeView].
 *       - **SearchScreen**: Shows the [
 */
@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    startDestination: NavDestinations = NavDestinations.MoviesScreen
) {
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar() {
                NavBarDestinations.destinations.forEach { view ->
                    NavigationBarItem(
                        selected = currentRoute?.hierarchy?.any { navDestination ->
                            navDestination.route?.equals(view.route) == true } == true,
                        onClick = {
                            val popUp = navHostController.graph.findStartDestination().id

                            navHostController.navigate(view.route) {
                                popUpTo(popUp) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = view.icon,
                                contentDescription = view.title
                            )
                        },
                        label = {
                            Text(
                                text = view.title,
                                fontFamily = nunitoFont,
                                modifier = Modifier.semantics { contentDescription = view.title }
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            navController = navHostController,
            startDestination = startDestination
        ) {
            composable<NavDestinations.MoviesScreen> {
                HomeView(navController = navHostController)
            }

            composable<NavDestinations.SearchScreen> {
                SearchView(navController = navHostController)
            }

            composable<NavDestinations.FavoriteScreen> {
                FavoritesView()
            }

            composable<NavDestinations.DetailMovieScreen> { navBackStackEntry ->
                val args = navBackStackEntry.toRoute<NavDestinations.DetailMovieScreen>()

                MovieDetailView(
                    id = args.id,
                    navController = navHostController
                )
            }
            composable<NavDestinations.CollectionScreen> { navBackStackEntry ->
                val args = navBackStackEntry.toRoute<NavDestinations.CollectionScreen>()

                CollectionScreen(
                    collectionId = args.id,
                    navController = navHostController
                )
            }
            composable<NavDestinations.SettingsScreen> {
                SettingsView()
            }
        }
    }
}