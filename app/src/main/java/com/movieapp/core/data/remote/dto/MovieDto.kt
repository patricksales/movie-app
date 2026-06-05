package com.movieapp.core.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.movieapp.features.home.domain.model.Movie

data class MovieDto(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    val overview: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>
) {
    fun toDomain(isFavorite: Boolean = false): Movie {
        return Movie(
            id = id,
            title = title,
            posterPath = posterPath,
            overview = overview,
            voteAverage = voteAverage,
            releaseDate = releaseDate.orEmpty(),
            genreIds = genreIds,
            isFavorite = isFavorite
        )
    }
}

data class MovieListResponse(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
