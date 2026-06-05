package com.movieapp.features.moviedetail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.movieapp.features.home.domain.model.Genre
import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.moviedetail.domain.model.MovieDetail
import com.movieapp.features.home.domain.data.repository.MovieRepository
import com.movieapp.features.moviedetail.domain.usecase.GetMovieDetailUseCase
import com.movieapp.features.home.domain.usecase.ToggleFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private lateinit var movieRepository: MovieRepository
    private lateinit var savedStateHandle: SavedStateHandle

    private val sampleDetail = MovieDetail(
        id = 1,
        title = "Test Movie",
        posterPath = "/poster.jpg",
        backdropPath = "/backdrop.jpg",
        overview = "Test overview",
        voteAverage = 8.5,
        releaseDate = "2024-01-01",
        genres = listOf(Genre(28, "Ação"), Genre(12, "Aventura")),
        runtime = 120,
        isFavorite = false
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getMovieDetailUseCase = mockk()
        toggleFavoriteUseCase = mockk(relaxed = true)
        movieRepository = mockk()
        savedStateHandle = SavedStateHandle(mapOf("movieId" to 1))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): MovieDetailViewModel {
        return MovieDetailViewModel(
            savedStateHandle,
            getMovieDetailUseCase,
            toggleFavoriteUseCase,
            movieRepository
        )
    }

    @Test
    fun `load movie detail success`() = runTest(testDispatcher) {
        coEvery { getMovieDetailUseCase(1) } returns Result.success(sampleDetail)

        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertNotNull(state.movieDetail)
        assertEquals("Test Movie", state.movieDetail?.title)
        assertEquals(2, state.movieDetail?.genres?.size)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `load movie detail failure sets error`() = runTest(testDispatcher) {
        coEvery { getMovieDetailUseCase(1) } returns Result.failure(RuntimeException("API Error"))

        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertNull(state.movieDetail)
        assertEquals("API Error", state.error)
        assertFalse(state.isLoading)
    }

    @Test
    fun `toggle favorite calls use case and updates state`() = runTest(testDispatcher) {
        coEvery { getMovieDetailUseCase(1) } returns Result.success(sampleDetail)
        coEvery { movieRepository.isFavorite(1) } returns true

        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onToggleFavorite()
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { toggleFavoriteUseCase(any<Movie>()) }
        assertTrue(viewModel.uiState.value.isFavorite)
    }

    @Test
    fun `movie detail has correct genres`() = runTest(testDispatcher) {
        coEvery { getMovieDetailUseCase(1) } returns Result.success(sampleDetail)

        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val genres = viewModel.uiState.value.movieDetail?.genres
        assertEquals("Ação", genres?.get(0)?.name)
        assertEquals("Aventura", genres?.get(1)?.name)
    }

    @Test
    fun `loading state is true initially`() = runTest(testDispatcher) {
        coEvery { getMovieDetailUseCase(1) } returns Result.success(sampleDetail)

        val viewModel = createViewModel()

        assertTrue(viewModel.uiState.value.isLoading)

        testDispatcher.scheduler.advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `retry after error reloads detail`() = runTest(testDispatcher) {
        coEvery { getMovieDetailUseCase(1) } returns Result.failure(RuntimeException("Error")) andThen Result.success(sampleDetail)

        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        assertNotNull(viewModel.uiState.value.error)

        viewModel.loadMovieDetail()
        testDispatcher.scheduler.advanceUntilIdle()

        assertNotNull(viewModel.uiState.value.movieDetail)
        assertNull(viewModel.uiState.value.error)
    }
}
