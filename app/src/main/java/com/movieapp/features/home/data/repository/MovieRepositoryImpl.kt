package com.movieapp.features.home.data.repository

import com.movieapp.core.data.local.dao.FavoriteMovieDao
import com.movieapp.core.data.local.entity.FavoriteMovieEntity
import com.movieapp.core.data.remote.TmdbApi
import com.movieapp.features.home.domain.model.Genre
import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.moviedetail.domain.model.MovieDetail
import com.movieapp.features.home.domain.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val api: TmdbApi,
    private val favoriteMovieDao: FavoriteMovieDao
) : MovieRepository {

    private var cachedGenres: List<Genre>? = null

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val favoriteIds = favoriteMovieDao.getAllFavoriteIds().first().toSet()
        return api.getPopularMovies(page).results.map { dto ->
            dto.toDomain(isFavorite = dto.id in favoriteIds)
        }
    }

    override suspend fun searchMovies(query: String, page: Int): List<Movie> {
        return api.searchMovies(query, page).results.map { it.toDomain() }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        val isFav = favoriteMovieDao.isFavorite(movieId)
        return api.getMovieDetail(movieId).toDomain(isFavorite = isFav)
    }

    override suspend fun getGenres(): List<Genre> {
        cachedGenres?.let { return it }
        val genres = api.getGenres().genres.map { it.toDomain() }
        cachedGenres = genres
        return genres
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMovieDao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(movie: Movie) {
        if (favoriteMovieDao.isFavorite(movie.id)) {
            favoriteMovieDao.deleteFavoriteById(movie.id)
        } else {
            favoriteMovieDao.insertFavorite(FavoriteMovieEntity.fromDomain(movie))
        }
    }

    override suspend fun isFavorite(movieId: Int): Boolean {
        return favoriteMovieDao.isFavorite(movieId)
    }

    override fun getFavoriteIds(): Flow<Set<Int>> {
        return favoriteMovieDao.getAllFavoriteIds().map { it.toSet() }
    }
}
