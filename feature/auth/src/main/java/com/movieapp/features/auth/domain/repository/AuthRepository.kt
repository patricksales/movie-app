package com.movieapp.features.auth.domain.repository

interface AuthRepository {
    suspend fun login(username: String, password: String): Boolean
    fun isLoggedIn(): Boolean
    fun isBiometricEnabled(): Boolean
    fun setBiometricEnabled(enabled: Boolean)
    fun saveAuthToken(token: String)
    fun getAuthToken(): String?
    fun logout()
}
