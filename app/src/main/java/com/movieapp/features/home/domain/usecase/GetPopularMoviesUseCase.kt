package com.movieapp.features.home.domain.usecase

import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.home.domain.data.repository.MovieRepository

class GetPopularMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int): Result<List<Movie>> {
        return try {
            Result.success(repository.getPopularMovies(page))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
