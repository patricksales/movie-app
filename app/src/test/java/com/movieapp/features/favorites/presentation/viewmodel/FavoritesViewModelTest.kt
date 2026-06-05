package com.movieapp.features.favorites.presentation.viewmodel

import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.favorites.domain.usecase.GetFavoriteMoviesUseCase
import com.movieapp.features.home.domain.usecase.ToggleFavoriteUseCase
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
class FavoritesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    private val sampleFavorites = listOf(
        Movie(1, "Fav Movie 1", "/poster1.jpg", "Overview 1", 8.0, "2024-01-01", listOf(28), isFavorite = true),
        Movie(2, "Fav Movie 2", "/poster2.jpg", "Overview 2", 7.5, "2024-02-01", listOf(35), isFavorite = true)
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getFavoriteMoviesUseCase = mockk()
        toggleFavoriteUseCase = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loads favorites on init`() = runTest(testDispatcher) {
        every { getFavoriteMoviesUseCase() } returns flowOf(sampleFavorites)

        val viewModel = FavoritesViewModel(getFavoriteMoviesUseCase, toggleFavoriteUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(2, state.favorites.size)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `empty favorites shows empty list`() = runTest(testDispatcher) {
        every { getFavoriteMoviesUseCase() } returns flowOf(emptyList())

        val viewModel = FavoritesViewModel(getFavoriteMoviesUseCase, toggleFavoriteUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value.favorites.isEmpty())
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `remove favorite calls toggle use case`() = runTest(testDispatcher) {
        every { getFavoriteMoviesUseCase() } returns flowOf(sampleFavorites)

        val viewModel = FavoritesViewModel(getFavoriteMoviesUseCase, toggleFavoriteUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onRemoveFavorite(sampleFavorites[0])
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { toggleFavoriteUseCase(sampleFavorites[0]) }
    }

    @Test
    fun `all favorites are marked as favorite`() = runTest(testDispatcher) {
        every { getFavoriteMoviesUseCase() } returns flowOf(sampleFavorites)

        val viewModel = FavoritesViewModel(getFavoriteMoviesUseCase, toggleFavoriteUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.value.favorites.forEach { movie ->
            assertTrue(movie.isFavorite)
        }
    }
}
