package com.movieapp.features.home.data.repository

import com.movieapp.core.data.local.dao.FavoriteMovieDao
import com.movieapp.core.data.local.entity.FavoriteMovieEntity
import com.movieapp.core.data.remote.TmdbApi
import com.movieapp.core.data.remote.dto.GenreDto
import com.movieapp.core.data.remote.dto.GenreListResponse
import com.movieapp.core.data.remote.dto.MovieDetailDto
import com.movieapp.core.data.remote.dto.MovieDto
import com.movieapp.core.data.remote.dto.MovieListResponse
import com.movieapp.features.home.domain.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var api: TmdbApi
    private lateinit var dao: FavoriteMovieDao
    private lateinit var repository: MovieRepositoryImpl

    private val sampleMovieDto = MovieDto(
        id = 1,
        title = "Test Movie",
        posterPath = "/poster.jpg",
        overview = "Test overview",
        voteAverage = 8.0,
        releaseDate = "2024-01-01",
        genreIds = listOf(28, 12)
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        api = mockk()
        dao = mockk(relaxed = true)
        every { dao.getAllFavoriteIds() } returns flowOf(emptyList())
        repository = MovieRepositoryImpl(api, dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchMovies returns mapped movies`() = runTest(testDispatcher) {
        coEvery { api.searchMovies("test", 1, any()) } returns MovieListResponse(
            page = 1,
            results = listOf(sampleMovieDto),
            totalPages = 1,
            totalResults = 1
        )

        val movies = repository.searchMovies("test", 1)

        assertEquals(1, movies.size)
        assertEquals("Test Movie", movies[0].title)
        assertEquals(8.0, movies[0].voteAverage, 0.01)
    }

    @Test
    fun `getMovieDetail returns mapped detail`() = runTest(testDispatcher) {
        val detailDto = MovieDetailDto(
            id = 1,
            title = "Detail Movie",
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            overview = "Detailed overview",
            voteAverage = 9.0,
            releaseDate = "2024-01-01",
            genres = listOf(GenreDto(28, "Ação")),
            runtime = 120
        )
        coEvery { api.getMovieDetail(1, any()) } returns detailDto
        coEvery { dao.isFavorite(1) } returns false

        val detail = repository.getMovieDetail(1)

        assertEquals("Detail Movie", detail.title)
        assertEquals(1, detail.genres.size)
        assertEquals("Ação", detail.genres[0].name)
        assertEquals(120, detail.runtime)
    }

    @Test
    fun `getGenres returns and caches genres`() = runTest(testDispatcher) {
        coEvery { api.getGenres(any()) } returns GenreListResponse(
            genres = listOf(GenreDto(28, "Ação"), GenreDto(12, "Aventura"))
        )

        val genres = repository.getGenres()
        assertEquals(2, genres.size)

        val cachedGenres = repository.getGenres()
        assertEquals(2, cachedGenres.size)

        coVerify(exactly = 1) { api.getGenres(any()) }
    }

    @Test
    fun `toggleFavorite inserts when not favorite`() = runTest(testDispatcher) {
        val movie = Movie(1, "Movie", "/poster.jpg", "Overview", 8.0, "2024-01-01", listOf(28))
        coEvery { dao.isFavorite(1) } returns false

        repository.toggleFavorite(movie)

        coVerify { dao.insertFavorite(any()) }
    }

    @Test
    fun `toggleFavorite deletes when already favorite`() = runTest(testDispatcher) {
        val movie = Movie(1, "Movie", "/poster.jpg", "Overview", 8.0, "2024-01-01", listOf(28))
        coEvery { dao.isFavorite(1) } returns true

        repository.toggleFavorite(movie)

        coVerify { dao.deleteFavoriteById(1) }
    }

    @Test
    fun `isFavorite delegates to dao`() = runTest(testDispatcher) {
        coEvery { dao.isFavorite(1) } returns true
        coEvery { dao.isFavorite(2) } returns false

        assertTrue(repository.isFavorite(1))
        assertFalse(repository.isFavorite(2))
    }

    @Test
    fun `getFavoriteMovies maps entities to domain`() = runTest(testDispatcher) {
        val entity = FavoriteMovieEntity(1, "Movie", "/poster.jpg", "Overview", 8.0, "2024-01-01", "28,12")
        every { dao.getAllFavorites() } returns flowOf(listOf(entity))

        val favorites = repository.getFavoriteMovies().first()

        assertEquals(1, favorites.size)
        assertEquals("Movie", favorites[0].title)
        assertTrue(favorites[0].isFavorite)
        assertEquals(listOf(28, 12), favorites[0].genreIds)
    }

    @Test
    fun `getFavoriteIds returns set of ids`() = runTest(testDispatcher) {
        every { dao.getAllFavoriteIds() } returns flowOf(listOf(1, 2, 3))

        val ids = repository.getFavoriteIds().first()

        assertEquals(setOf(1, 2, 3), ids)
    }
}
