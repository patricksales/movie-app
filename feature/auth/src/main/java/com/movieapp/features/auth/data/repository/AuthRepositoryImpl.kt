package com.movieapp.features.auth.data.repository

import com.movieapp.features.auth.domain.repository.AuthRepository
import com.movieapp.core.security.SecurePreferences

class AuthRepositoryImpl(
    private val securePreferences: SecurePreferences
) : AuthRepository {

    override suspend fun login(username: String, password: String): Boolean {
        return if (username == MOCK_USERNAME && password == MOCK_PASSWORD) {
            saveAuthToken("mock_token_${System.currentTimeMillis()}")
            true
        } else {
            false
        }
    }

    override fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    override fun isBiometricEnabled(): Boolean {
        return securePreferences.getBoolean(KEY_BIOMETRIC_ENABLED, false)
    }

    override fun setBiometricEnabled(enabled: Boolean) {
        securePreferences.putBoolean(KEY_BIOMETRIC_ENABLED, enabled)
    }

    override fun saveAuthToken(token: String) {
        securePreferences.putString(KEY_AUTH_TOKEN, token)
    }

    override fun getAuthToken(): String? {
        return securePreferences.getString(KEY_AUTH_TOKEN, null)
    }

    override fun logout() {
        securePreferences.remove(KEY_AUTH_TOKEN)
    }

    companion object {
        private const val MOCK_USERNAME = "admin"
        private const val MOCK_PASSWORD = "1234"
        private const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"
        private const val KEY_AUTH_TOKEN = "auth_token"
    }
}
