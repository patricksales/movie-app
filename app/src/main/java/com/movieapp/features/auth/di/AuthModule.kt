package com.movieapp.features.auth.di

import com.movieapp.features.auth.data.repository.AuthRepositoryImpl
import com.movieapp.features.auth.domain.repository.AuthRepository
import com.movieapp.features.auth.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    viewModel { LoginViewModel(get(), get()) }
}
