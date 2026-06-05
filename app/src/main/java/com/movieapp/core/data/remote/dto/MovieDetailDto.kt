package com.movieapp.core.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.movieapp.features.moviedetail.domain.model.GenreDetail
import com.movieapp.features.moviedetail.domain.model.MovieDetail

data class MovieDetailDto(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    val overview: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String?,
    val genres: List<GenreDetailDto>,
    val runtime: Int?
) {
    fun toDomain(isFavorite: Boolean = false): MovieDetail {
        return MovieDetail(
            id = id,
            title = title,
            posterPath = posterPath,
            backdropPath = backdropPath,
            overview = overview,
            voteAverage = voteAverage,
            releaseDate = releaseDate.orEmpty(),
            genres = genres.map { it.toDomain() },
            runtime = runtime,
            isFavorite = isFavorite
        )
    }
}
