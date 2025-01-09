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
import com.maxidev.moviehub.feature.collection.presentation.CollectionScreen
import com.maxidev.moviehub.feature.detail.presentation.MovieDetailView
import com.maxidev.moviehub.feature.favorite.presentation.FavoritesView
import com.maxidev.moviehub.feature.home.presentation.HomeView
import com.maxidev.moviehub.feature.search.presentation.SearchView

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    startDestination: NavDestinations = NavDestinations.MoviesScreen
) {
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
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
        }
    }
}