package com.movieapp.features.moviedetail.domain.model

import com.movieapp.features.home.domain.model.Genre

data class MovieDetail(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String,
    val voteAverage: Double,
    val releaseDate: String,
    val genres: List<Genre>,
    val runtime: Int?,
    val isFavorite: Boolean = false
)
