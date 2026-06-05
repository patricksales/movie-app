package com.movieapp.features.moviedetail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.moviedetail.domain.model.MovieDetail
import com.movieapp.features.home.domain.data.repository.MovieRepository
import com.movieapp.features.moviedetail.domain.usecase.GetMovieDetailUseCase
import com.movieapp.features.home.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFavorite: Boolean = false
)

class MovieDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetail()
    }

    fun loadMovieDetail() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            getMovieDetailUseCase(movieId).fold(
                onSuccess = { detail ->
                    _uiState.value = _uiState.value.copy(
                        movieDetail = detail,
                        isLoading = false,
                        isFavorite = detail.isFavorite
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Erro ao carregar detalhes"
                    )
                }
            )
        }
    }

    fun onToggleFavorite() {
        val detail = _uiState.value.movieDetail ?: return
        viewModelScope.launch {
            val movie = Movie(
                id = detail.id,
                title = detail.title,
                posterPath = detail.posterPath,
                overview = detail.overview,
                voteAverage = detail.voteAverage,
                releaseDate = detail.releaseDate,
                genreIds = detail.genres.map { it.id },
                isFavorite = _uiState.value.isFavorite
            )
            toggleFavoriteUseCase(movie)
            val newFavState = movieRepository.isFavorite(movieId)
            _uiState.value = _uiState.value.copy(isFavorite = newFavState)
        }
    }
}
