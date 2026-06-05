package com.movieapp.core.data.remote.dto

import com.movieapp.features.moviedetail.domain.model.GenreDetail

data class GenreDetailDto(
    val id: Int,
    val name: String
) {
    fun toDomain(): GenreDetail = GenreDetail(id = id, name = name)
}

data class GenreDetailListResponse(
    val genres: List<GenreDetailDto>
)
