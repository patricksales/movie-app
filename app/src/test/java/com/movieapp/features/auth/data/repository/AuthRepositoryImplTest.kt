package com.movieapp.features.auth.data.repository

import com.movieapp.core.security.SecurePreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryImplTest {

    private lateinit var securePreferences: SecurePreferences
    private lateinit var repository: AuthRepositoryImpl

    @Before
    fun setUp() {
        securePreferences = mockk(relaxed = true)
        repository = AuthRepositoryImpl(securePreferences)
    }

    @Test
    fun `login with correct credentials returns true`() = runTest {
        val result = repository.login("admin", "1234")
        assertTrue(result)
    }

    @Test
    fun `login with wrong credentials returns false`() = runTest {
        val result = repository.login("wrong", "wrong")
        assertFalse(result)
    }

    @Test
    fun `login success saves auth token`() = runTest {
        repository.login("admin", "1234")
        verify { securePreferences.putString("auth_token", any()) }
    }

    @Test
    fun `isLoggedIn returns true when token exists`() {
        every { securePreferences.getString("auth_token", null) } returns "some_token"
        assertTrue(repository.isLoggedIn())
    }

    @Test
    fun `isLoggedIn returns false when no token`() {
        every { securePreferences.getString("auth_token", null) } returns null
        assertFalse(repository.isLoggedIn())
    }

    @Test
    fun `setBiometricEnabled saves preference`() {
        repository.setBiometricEnabled(true)
        verify { securePreferences.putBoolean("biometric_enabled", true) }
    }

    @Test
    fun `isBiometricEnabled reads preference`() {
        every { securePreferences.getBoolean("biometric_enabled", false) } returns true
        assertTrue(repository.isBiometricEnabled())
    }

    @Test
    fun `logout removes auth token`() {
        repository.logout()
        verify { securePreferences.remove("auth_token") }
    }

    @Test
    fun `login with empty username fails`() = runTest {
        val result = repository.login("", "1234")
        assertFalse(result)
    }

    @Test
    fun `login with empty password fails`() = runTest {
        val result = repository.login("admin", "")
        assertFalse(result)
    }

    @Test
    fun `getAuthToken returns stored token`() {
        every { securePreferences.getString("auth_token", null) } returns "test_token"
        val token = repository.getAuthToken()
        assertTrue(token == "test_token")
    }
}
