package com.movieapp.features.favorites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.favorites.domain.usecase.GetFavoriteMoviesUseCase
import com.movieapp.features.home.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val favorites: List<Movie> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class FavoritesViewModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        getFavoriteMoviesUseCase()
            .onEach { movies ->
                _uiState.value = _uiState.value.copy(
                    favorites = movies,
                    isLoading = false,
                    error = null
                )
            }
            .catch { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Erro ao carregar favoritos"
                )
            }
            .launchIn(viewModelScope)
    }

    fun onRemoveFavorite(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movie)
        }
    }
}
