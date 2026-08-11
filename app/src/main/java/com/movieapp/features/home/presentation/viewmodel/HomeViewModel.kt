package com.movieapp.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapp.features.home.domain.model.Genre
import com.movieapp.features.home.domain.model.Movie
import com.movieapp.features.home.domain.data.repository.MovieRepository
import com.movieapp.features.home.domain.usecase.GetGenresUseCase
import com.movieapp.features.home.domain.usecase.GetPopularMoviesUseCase
import com.movieapp.features.home.domain.usecase.SearchMoviesUseCase
import com.movieapp.features.home.domain.usecase.ToggleFavoriteUseCase
import com.movieapp.features.monitoring.PerformanceMonitoring
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val genres: Map<Int, String> = emptyMap(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasMorePages: Boolean = true,
    val isSearchActive: Boolean = false
)

@OptIn(FlowPreview::class)
class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private var loadJob: Job? = null

    init {
        loadGenres()
        loadPopularMovies()
        observeFavorites()
        observeSearch()
    }

    private fun observeFavorites() {
        movieRepository.getFavoriteIds()
            .onEach { favoriteIds ->
                val currentMovies = _uiState.value.movies.map { movie ->
                    movie.copy(isFavorite = movie.id in favoriteIds)
                }
                _uiState.value = _uiState.value.copy(movies = currentMovies)
            }
            .launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        _searchQuery
            .debounce(500)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isBlank()) {
                    _uiState.value = _uiState.value.copy(
                        isSearchActive = false,
                        movies = emptyList(),
                        currentPage = 1,
                        hasMorePages = true,
                        error = null
                    )
                    loadPopularMovies()
                } else {
                    _uiState.value = _uiState.value.copy(
                        isSearchActive = true,
                        movies = emptyList(),
                        currentPage = 1,
                        hasMorePages = true,
                        error = null
                    )
                    searchMovies(query, 1)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase().onSuccess { genres ->
                _uiState.value = _uiState.value.copy(
                    genres = genres.associate { it.id to it.name }
                )
            }
        }
    }

    fun loadPopularMovies() {
        if (_uiState.value.isLoading || _uiState.value.isSearchActive) return

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val trace = try { PerformanceMonitoring.traceNetworkRequest("load_popular_movies") } catch (e: Throwable) { null }
            getPopularMoviesUseCase(1).fold(
                onSuccess = { movies ->
                    try { trace?.javaClass?.getMethod("stop")?.invoke(trace) } catch (_: Throwable) {}
                    val favoriteIds = try { movieRepository.getFavoriteIds().first() } catch (_: Throwable) { emptySet<Int>() }
                    val mappedMovies = movies.map { it.copy(isFavorite = it.id in favoriteIds) }
                    _uiState.value = _uiState.value.copy(
                        movies = mappedMovies,
                        isLoading = false,
                        currentPage = 1,
                        hasMorePages = mappedMovies.isNotEmpty()
                    )
                },

                onFailure = { exception ->
                    try { trace?.javaClass?.getMethod("stop")?.invoke(trace) } catch (_: Throwable) {}
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Erro desconhecido"
                    )
                }
            )
        }
    }

    fun loadNextPage() {
        val state = _uiState.value
        if (state.isLoading || state.isLoadingMore || !state.hasMorePages) return

        val nextPage = state.currentPage + 1

        viewModelScope.launch {
            _uiState.value = state.copy(isLoadingMore = true)

            val result = if (state.isSearchActive && state.searchQuery.isNotBlank()) {
                searchMoviesUseCase(state.searchQuery, nextPage)
            } else {
                getPopularMoviesUseCase(nextPage)
            }

            result.fold(
                onSuccess = { newMovies ->
                    val favoriteIds = try { movieRepository.getFavoriteIds().first() } catch (_: Throwable) { emptySet<Int>() }
                    val mappedNew = newMovies.map { it.copy(isFavorite = it.id in favoriteIds) }
                    _uiState.value = _uiState.value.copy(
                        movies = _uiState.value.movies + mappedNew,
                        isLoadingMore = false,
                        currentPage = nextPage,
                        hasMorePages = mappedNew.isNotEmpty()
                    )
                },

                onFailure = {
                    _uiState.value = _uiState.value.copy(isLoadingMore = false)
                }
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        _searchQuery.value = query
    }

    private fun searchMovies(query: String, page: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = page == 1, error = null)

            searchMoviesUseCase(query, page).fold(
                onSuccess = { movies ->
                    val favoriteIds = try { movieRepository.getFavoriteIds().first() } catch (_: Throwable) { emptySet<Int>() }
                    val mapped = movies.map { it.copy(isFavorite = it.id in favoriteIds) }
                    _uiState.value = _uiState.value.copy(
                        movies = if (page == 1) mapped else _uiState.value.movies + mapped,
                        isLoading = false,
                        currentPage = page,
                        hasMorePages = mapped.isNotEmpty()
                    )
                },

                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Erro na busca"
                    )
                }
            )
        }
    }

    fun onFavoriteClick(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movie)
        }
    }

    fun getGenreNames(genreIds: List<Int>): String {
        return genreIds.mapNotNull { _uiState.value.genres[it] }.joinToString(", ")
    }
}
