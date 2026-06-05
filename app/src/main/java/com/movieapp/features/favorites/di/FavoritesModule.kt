package com.movieapp.features.favorites.di

import com.movieapp.features.favorites.domain.usecase.GetFavoriteMoviesUseCase
import com.movieapp.features.favorites.presentation.viewmodel.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    factory { GetFavoriteMoviesUseCase(get()) }

    viewModel { FavoritesViewModel(get(), get()) }
}
