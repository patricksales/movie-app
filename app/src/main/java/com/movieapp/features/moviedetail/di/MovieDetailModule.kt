package com.movieapp.features.moviedetail.di

import com.movieapp.features.moviedetail.domain.usecase.GetMovieDetailUseCase
import com.movieapp.features.moviedetail.domain.usecase.IsFavoriteUseCase
import com.movieapp.features.moviedetail.presentation.viewmodel.MovieDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieDetailModule = module {
    factory { GetMovieDetailUseCase(get()) }
    factory { IsFavoriteUseCase(get()) }

    viewModel { params -> MovieDetailViewModel(params.get(), get(), get(), get(), get()) }
}
