package com.movieapp.features.favorites.domain.usecase

import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.home.domain.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteMoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> {
        return repository.getFavoriteMovies()
    }
}
