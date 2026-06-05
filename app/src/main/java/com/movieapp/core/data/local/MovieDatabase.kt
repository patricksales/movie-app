package com.movieapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movieapp.core.data.local.dao.FavoriteMovieDao
import com.movieapp.core.data.local.entity.FavoriteMovieEntity

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
