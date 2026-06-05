package com.movieapp.features.home.domain.usecase

import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.home.domain.data.repository.MovieRepository

class SearchMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int): Result<List<Movie>> {
        return try {
            Result.success(repository.searchMovies(query, page))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
