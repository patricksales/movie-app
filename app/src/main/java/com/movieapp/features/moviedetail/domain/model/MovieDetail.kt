package com.movieapp.features.moviedetail.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String,
    val voteAverage: Double,
    val releaseDate: String,
    val genres: List<GenreDetail>,
    val runtime: Int?,
    val isFavorite: Boolean = false
)
