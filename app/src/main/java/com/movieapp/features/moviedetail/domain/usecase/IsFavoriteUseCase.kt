package com.movieapp.features.moviedetail.domain.usecase

import com.movieapp.features.home.domain.data.repository.MovieRepository

class IsFavoriteUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Boolean {
        return repository.isFavorite(movieId)
    }
}
