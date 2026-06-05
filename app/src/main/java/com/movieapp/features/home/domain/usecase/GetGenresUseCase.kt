package com.movieapp.features.home.domain.usecase

import com.movieapp.features.home.domain.model.Genre
import com.movieapp.features.home.domain.data.repository.MovieRepository

class GetGenresUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Result<List<Genre>> {
        return try {
            Result.success(repository.getGenres())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
