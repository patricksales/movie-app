package com.movieapp.features.auth.presentation.viewmodel

import app.cash.turbine.test
import com.movieapp.features.auth.domain.repository.AuthRepository
import com.movieapp.core.security.BiometricHelper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var authRepository: AuthRepository
    private lateinit var biometricHelper: BiometricHelper
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mockk(relaxed = true)
        biometricHelper = mockk(relaxed = true)
        every { authRepository.isLoggedIn() } returns false
        every { authRepository.isBiometricEnabled() } returns false
        every { biometricHelper.canAuthenticate() } returns BiometricHelper.BiometricStatus.NO_HARDWARE
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): LoginViewModel {
        return LoginViewModel(authRepository, biometricHelper)
    }

    @Test
    fun `initial state has empty fields and no biometric mode`() {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("", state.username)
        assertEquals("", state.password)
        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
        assertFalse(state.isBiometricMode)
        assertFalse(state.showEnableBiometricDialog)
    }

    @Test
    fun `onUsernameChange updates username`() {
        viewModel = createViewModel()
        viewModel.onUsernameChange("admin")
        assertEquals("admin", viewModel.uiState.value.username)
    }

    @Test
    fun `onPasswordChange updates password`() {
        viewModel = createViewModel()
        viewModel.onPasswordChange("1234")
        assertEquals("1234", viewModel.uiState.value.password)
    }

    @Test
    fun `login with correct credentials and no biometric emits NavigateToHome`() = runTest(testDispatcher) {
        coEvery { authRepository.login("admin", "1234") } returns true
        every { biometricHelper.canAuthenticate() } returns BiometricHelper.BiometricStatus.NO_HARDWARE

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onUsernameChange("admin")
        viewModel.onPasswordChange("1234")

        viewModel.events.test {
            viewModel.onLoginClick()
            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(LoginEvent.NavigateToHome, awaitItem())
        }
    }

    @Test
    fun `login with wrong credentials shows error`() = runTest(testDispatcher) {
        coEvery { authRepository.login("wrong", "wrong") } returns false

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onUsernameChange("wrong")
        viewModel.onPasswordChange("wrong")
        viewModel.onLoginClick()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Usuário ou senha inválidos", viewModel.uiState.value.errorMessage)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `login success with biometric available shows enable dialog`() = runTest(testDispatcher) {
        coEvery { authRepository.login("admin", "1234") } returns true
        every { authRepository.isBiometricEnabled() } returns false
        every { biometricHelper.canAuthenticate() } returns BiometricHelper.BiometricStatus.AVAILABLE

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onUsernameChange("admin")
        viewModel.onPasswordChange("1234")
        viewModel.onLoginClick()
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value.showEnableBiometricDialog)
    }

    @Test
    fun `enabling biometric saves preference and navigates`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.events.test {
            viewModel.onEnableBiometric(true)
            testDispatcher.scheduler.advanceUntilIdle()

            verify { authRepository.setBiometricEnabled(true) }
            assertEquals(LoginEvent.NavigateToHome, awaitItem())
        }
    }

    @Test
    fun `declining biometric saves preference and navigates`() = runTest(testDispatcher) {
        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.events.test {
            viewModel.onEnableBiometric(false)
            testDispatcher.scheduler.advanceUntilIdle()

            verify { authRepository.setBiometricEnabled(false) }
            assertEquals(LoginEvent.NavigateToHome, awaitItem())
        }
    }

    @Test
    fun `biometric mode activated when logged in and biometric enabled`() {
        every { authRepository.isLoggedIn() } returns true
        every { authRepository.isBiometricEnabled() } returns true
        every { biometricHelper.canAuthenticate() } returns BiometricHelper.BiometricStatus.AVAILABLE

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value.isBiometricMode)
    }

    @Test
    fun `biometric mode not activated when biometric not available`() {
        every { authRepository.isLoggedIn() } returns true
        every { authRepository.isBiometricEnabled() } returns true
        every { biometricHelper.canAuthenticate() } returns BiometricHelper.BiometricStatus.NO_HARDWARE

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isBiometricMode)
    }

    @Test
    fun `biometric success navigates to home`() = runTest(testDispatcher) {
        every { authRepository.isLoggedIn() } returns true
        every { authRepository.isBiometricEnabled() } returns true
        every { biometricHelper.canAuthenticate() } returns BiometricHelper.BiometricStatus.AVAILABLE

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.events.test {
            viewModel.onBiometricSuccess()
            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(LoginEvent.NavigateToHome, awaitItem())
        }
    }

    @Test
    fun `switch to password login clears biometric mode and logs out`() {
        every { authRepository.isLoggedIn() } returns true
        every { authRepository.isBiometricEnabled() } returns true
        every { biometricHelper.canAuthenticate() } returns BiometricHelper.BiometricStatus.AVAILABLE

        viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value.isBiometricMode)

        viewModel.onSwitchToPasswordLogin()

        assertFalse(viewModel.uiState.value.isBiometricMode)
        verify { authRepository.logout() }
    }
}
