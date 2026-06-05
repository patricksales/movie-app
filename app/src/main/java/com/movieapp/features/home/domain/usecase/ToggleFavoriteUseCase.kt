package com.movieapp.features.home.domain.usecase

import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.home.domain.data.repository.MovieRepository

class ToggleFavoriteUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        repository.toggleFavorite(movie)
    }
}
