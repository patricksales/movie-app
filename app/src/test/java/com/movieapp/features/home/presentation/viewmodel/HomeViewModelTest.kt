package com.movieapp.features.home.presentation.viewmodel

import com.movieapp.features.home.domain.model.Genre
import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.home.domain.data.repository.MovieRepository
import com.movieapp.features.home.domain.usecase.GetGenresUseCase
import com.movieapp.features.home.domain.usecase.GetPopularMoviesUseCase
import com.movieapp.features.home.domain.usecase.SearchMoviesUseCase
import com.movieapp.features.home.domain.usecase.ToggleFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase
    private lateinit var getGenresUseCase: GetGenresUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private lateinit var movieRepository: MovieRepository

    private val sampleMovies = listOf(
        Movie(1, "Movie 1", "/poster1.jpg", "Overview 1", 8.0, "2024-01-01", listOf(28, 12)),
        Movie(2, "Movie 2", "/poster2.jpg", "Overview 2", 7.5, "2024-02-01", listOf(35))
    )

    private val sampleGenres = listOf(
        Genre(28, "Ação"),
        Genre(12, "Aventura"),
        Genre(35, "Comédia")
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPopularMoviesUseCase = mockk()
        searchMoviesUseCase = mockk()
        getGenresUseCase = mockk()
        toggleFavoriteUseCase = mockk(relaxed = true)
        movieRepository = mockk()

        every { movieRepository.getFavoriteIds() } returns flowOf(emptySet())
        coEvery { getPopularMoviesUseCase(1) } returns Result.success(sampleMovies)
        coEvery { getGenresUseCase() } returns Result.success(sampleGenres)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): HomeViewModel {
        return HomeViewModel(
            getPopularMoviesUseCase,
            searchMoviesUseCase,
            getGenresUseCase,
            toggleFavoriteUseCase,
            movieRepository
        )
    }

    @Test
    fun `initial load fetches popular movies`() = runTest(testDispatcher) {
        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(2, state.movies.size)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `initial load fetches genres`() = runTest(testDispatcher) {
        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(3, state.genres.size)
        assertEquals("Ação", state.genres[28])
    }

    @Test
    fun `getGenreNames maps ids to names`() = runTest(testDispatcher) {
        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val names = viewModel.getGenreNames(listOf(28, 12))
        assertEquals("Ação, Aventura", names)
    }

    @Test
    fun `load error sets error state`() = runTest(testDispatcher) {
        coEvery { getPopularMoviesUseCase(1) } returns Result.failure(RuntimeException("Network error"))

        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Network error", viewModel.uiState.value.error)
        assertTrue(viewModel.uiState.value.movies.isEmpty())
    }

    @Test
    fun `load next page appends movies`() = runTest(testDispatcher) {
        val page2Movies = listOf(
            Movie(3, "Movie 3", "/poster3.jpg", "Overview 3", 6.0, "2024-03-01", listOf(28))
        )
        coEvery { getPopularMoviesUseCase(2) } returns Result.success(page2Movies)

        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.loadNextPage()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(3, viewModel.uiState.value.movies.size)
        assertEquals(2, viewModel.uiState.value.currentPage)
    }

    @Test
    fun `toggle favorite calls use case`() = runTest(testDispatcher) {
        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onFavoriteClick(sampleMovies[0])
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { toggleFavoriteUseCase(sampleMovies[0]) }
    }

    @Test
    fun `search query triggers search`() = runTest(testDispatcher) {
        coEvery { searchMoviesUseCase("Batman", 1) } returns Result.success(
            listOf(Movie(10, "Batman", "/batman.jpg", "Overview", 9.0, "2022-01-01", listOf(28)))
        )

        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onSearchQueryChange("Batman")
        testDispatcher.scheduler.advanceTimeBy(600)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value.isSearchActive)
    }

    @Test
    fun `empty search query returns to popular movies`() = runTest(testDispatcher) {
        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onSearchQueryChange("")
        testDispatcher.scheduler.advanceTimeBy(600)
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isSearchActive)
    }

    @Test
    fun `favorite ids update movie favorite state`() = runTest(testDispatcher) {
        every { movieRepository.getFavoriteIds() } returns flowOf(setOf(1))

        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val movie1 = viewModel.uiState.value.movies.find { it.id == 1 }
        assertTrue(movie1?.isFavorite == true)
    }
}
