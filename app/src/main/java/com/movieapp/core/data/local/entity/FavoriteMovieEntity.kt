package com.movieapp.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.movieapp.features.home.domain.model.Movie

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val voteAverage: Double,
    val releaseDate: String,
    val genreIds: String
) {
    fun toDomain(): Movie {
        return Movie(
            id = id,
            title = title,
            posterPath = posterPath,
            overview = overview,
            voteAverage = voteAverage,
            releaseDate = releaseDate,
            genreIds = genreIds.split(",").filter { it.isNotEmpty() }.map { it.toInt() },
            isFavorite = true
        )
    }

    companion object {
        fun fromDomain(movie: Movie): FavoriteMovieEntity {
            return FavoriteMovieEntity(
                id = movie.id,
                title = movie.title,
                posterPath = movie.posterPath,
                overview = movie.overview,
                voteAverage = movie.voteAverage,
                releaseDate = movie.releaseDate,
                genreIds = movie.genreIds.joinToString(",")
            )
        }
    }
}
