package com.movieapp.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.movieapp.R
import com.movieapp.features.auth.presentation.ui.LoginScreen
import com.movieapp.features.moviedetail.presentation.ui.MovieDetailScreen
import com.movieapp.features.favorites.presentation.ui.FavoritesScreen
import com.movieapp.features.home.presentation.ui.HomeScreen

data class BottomNavItem(
    val screen: Screen,
    val labelResId: Int,
    val icon: @Composable () -> Unit
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        BottomNavItem(
            screen = Screen.Home,
            labelResId = R.string.home,
            icon = { Icon(Icons.Filled.Home, contentDescription = null) }
        ),
        BottomNavItem(
            screen = Screen.Favorites,
            labelResId = R.string.favorites,
            icon = { Icon(Icons.Filled.Favorite, contentDescription = null) }
        )
    )

    val showBottomBar = currentDestination?.hierarchy?.any { dest ->
        dest.route == Screen.Home.route || dest.route == Screen.Favorites.route
    } == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == item.screen.route
                        } == true
                        NavigationBarItem(
                            icon = item.icon,
                            label = { Text(stringResource(item.labelResId)) },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(Screen.MovieDetail.createRoute(movieId))
                    }
                )
            }

            composable(
                route = Screen.MovieDetail.route,
                arguments = listOf(
                    navArgument("movieId") { type = NavType.IntType }
                )
            ) {
                MovieDetailScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(Screen.MovieDetail.createRoute(movieId))
                    }
                )
            }
        }
    }
}
