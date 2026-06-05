package com.movieapp.features.home.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val voteAverage: Double,
    val releaseDate: String,
    val genreIds: List<Int>,
    val isFavorite: Boolean = false
)
