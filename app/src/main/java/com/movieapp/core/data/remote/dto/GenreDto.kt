package com.movieapp.core.data.remote.dto

import com.movieapp.features.home.domain.model.Genre

data class GenreDto(
    val id: Int,
    val name: String
) {
    fun toDomain(): Genre = Genre(id = id, name = name)
}

data class GenreListResponse(
    val genres: List<GenreDto>
)
