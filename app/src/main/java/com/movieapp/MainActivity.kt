package com.movieapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.movieapp.core.navigation.AppNavigation
import com.movieapp.core.ui.theme.MovieAppTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        FirebaseCrashlytics.getInstance().setUserId("user${System.currentTimeMillis()}")
        setContent {
            MovieAppTheme {
                AppNavigation()
            }
        }
    }
}
