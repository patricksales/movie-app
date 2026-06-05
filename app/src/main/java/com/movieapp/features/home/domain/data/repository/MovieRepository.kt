package com.movieapp.features.home.domain.data.repository

import com.movieapp.features.home.domain.model.Genre
import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.moviedetail.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun searchMovies(query: String, page: Int): List<Movie>
    suspend fun getMovieDetail(movieId: Int): MovieDetail
    suspend fun getGenres(): List<Genre>
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun toggleFavorite(movie: Movie)
    suspend fun isFavorite(movieId: Int): Boolean
    fun getFavoriteIds(): Flow<Set<Int>>
}
