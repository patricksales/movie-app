package com.movieapp.core.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object MovieDetail : Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: Int): String = "movie_detail/$movieId"
    }
    data object Favorites : Screen("favorites")
}
