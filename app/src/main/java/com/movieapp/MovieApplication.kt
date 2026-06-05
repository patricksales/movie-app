package com.movieapp

import android.app.Application
import com.movieapp.core.di.databaseModule
import com.movieapp.core.di.networkModule
import com.movieapp.core.di.securityModule
import com.movieapp.features.auth.di.authModule
import com.movieapp.features.favorites.di.favoritesModule
import com.movieapp.features.home.di.homeModule
import com.movieapp.features.moviedetail.di.movieDetailModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MovieApplication)
            modules(
                networkModule,
                databaseModule,
                securityModule,
                authModule,
                homeModule,
                movieDetailModule,
                favoritesModule
            )
        }
    }
}
