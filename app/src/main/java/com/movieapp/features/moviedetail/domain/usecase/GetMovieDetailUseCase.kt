package com.movieapp.features.moviedetail.domain.usecase

import com.movieapp.features.moviedetail.domain.model.MovieDetail
import com.movieapp.features.home.domain.data.repository.MovieRepository

class GetMovieDetailUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<MovieDetail> {
        return try {
            Result.success(repository.getMovieDetail(movieId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
