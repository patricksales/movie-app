package com.movieapp.core.data.remote

import com.movieapp.core.data.remote.dto.GenreListResponse
import com.movieapp.core.data.remote.dto.MovieDetailDto
import com.movieapp.core.data.remote.dto.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "pt-BR"
    ): MovieListResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language: String = "pt-BR"
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "pt-BR"
    ): MovieDetailDto

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String = "pt-BR"
    ): GenreListResponse
}
