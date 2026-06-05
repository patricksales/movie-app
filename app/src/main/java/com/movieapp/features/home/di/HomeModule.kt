package com.movieapp.features.home.di

import com.movieapp.features.home.data.repository.MovieRepositoryImpl
import com.movieapp.features.home.domain.data.repository.MovieRepository
import com.movieapp.features.home.domain.usecase.GetGenresUseCase
import com.movieapp.features.home.domain.usecase.GetPopularMoviesUseCase
import com.movieapp.features.home.domain.usecase.SearchMoviesUseCase
import com.movieapp.features.home.domain.usecase.ToggleFavoriteUseCase
import com.movieapp.features.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }

    factory { GetPopularMoviesUseCase(get()) }
    factory { SearchMoviesUseCase(get()) }
    factory { GetGenresUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }

    viewModel { HomeViewModel(get(), get(), get(), get(), get()) }
}
