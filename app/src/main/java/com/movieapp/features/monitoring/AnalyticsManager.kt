package com.movieapp.features.monitoring

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase

class AnalyticsManager(context: Context) {

    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    fun logLoginSuccess(username: String) {
        Firebase.analytics.logEvent("login_success") {
            param("username", username)
            param("timestamp", System.currentTimeMillis().toString())
        }
    }

    fun logLoginFailed(reason: String) {
        Firebase.analytics.logEvent("login_failed") {
            param("reason", reason)
        }
    }

    fun logBiometricEnabled() {
        Firebase.analytics.logEvent("biometric_enabled") {
            param("type", "fingerprint_or_face")
        }
    }

    fun logMovieViewed(movieId: Int, movieTitle: String) {
        Firebase.analytics.logEvent("movie_viewed") {
            param("movie_id", movieId.toLong())
            param("movie_title", movieTitle)
            param("timestamp", System.currentTimeMillis().toString())
        }
    }

    fun logMovieSearch(query: String, resultsCount: Int) {
        Firebase.analytics.logEvent("movie_search") {
            param("search_query", query)
            param("results_count", resultsCount.toLong())
        }
    }

    fun logMovieFavorited(movieId: Int, movieTitle: String) {
        Firebase.analytics.logEvent("movie_favorited") {
            param("movie_id", movieId.toLong())
            param("movie_title", movieTitle)
            param("action", "favorite")
        }
    }

    fun logMovieUnfavorited(movieId: Int, movieTitle: String) {
        Firebase.analytics.logEvent("movie_unfavorited") {
            param("movie_id", movieId.toLong())
            param("movie_title", movieTitle)
            param("action", "unfavorite")
        }
    }

    fun logMovieShared(movieId: Int, movieTitle: String) {
        Firebase.analytics.logEvent("movie_shared") {
            param("movie_id", movieId.toLong())
            param("movie_title", movieTitle)
            param("share_method", "social_media")
        }
    }

    fun logScreenView(screenName: String) {
        Firebase.analytics.logEvent("screen_view") {
            param("screen_name", screenName)
        }
    }

    fun logUserProperty(propertyName: String, value: String) {
        firebaseAnalytics.setUserProperty(propertyName, value)
    }
}
