package com.movieapp.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapp.features.auth.domain.repository.AuthRepository
import com.movieapp.core.security.BiometricHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isBiometricMode: Boolean = false,
    val showEnableBiometricDialog: Boolean = false
)

sealed class LoginEvent {
    data object NavigateToHome : LoginEvent()
}

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val biometricHelper: BiometricHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvent>()
    val events: SharedFlow<LoginEvent> = _events.asSharedFlow()

    init {
        checkBiometricLogin()
    }

    private fun checkBiometricLogin() {
        if (authRepository.isLoggedIn() && authRepository.isBiometricEnabled()) {
            val canAuth = biometricHelper.canAuthenticate()
            if (canAuth == BiometricHelper.BiometricStatus.AVAILABLE) {
                _uiState.value = _uiState.value.copy(isBiometricMode = true)
            }
        }
    }

    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(username = username, errorMessage = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, errorMessage = null)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val success = authRepository.login(
                _uiState.value.username.trim(),
                _uiState.value.password.trim()
            )

            if (success) {
                val canUseBiometric = biometricHelper.canAuthenticate() == BiometricHelper.BiometricStatus.AVAILABLE
                if (canUseBiometric && !authRepository.isBiometricEnabled()) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        showEnableBiometricDialog = true
                    )
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _events.emit(LoginEvent.NavigateToHome)
                }
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Usuário ou senha inválidos"
                )
            }
        }
    }

    fun onBiometricSuccess() {
        viewModelScope.launch {
            _events.emit(LoginEvent.NavigateToHome)
        }
    }

    fun onSwitchToPasswordLogin() {
        authRepository.logout()
        _uiState.value = _uiState.value.copy(
            isBiometricMode = false,
            username = "",
            password = ""
        )
    }

    fun onEnableBiometric(enabled: Boolean) {
        authRepository.setBiometricEnabled(enabled)
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(showEnableBiometricDialog = false)
            _events.emit(LoginEvent.NavigateToHome)
        }
    }

    fun dismissBiometricDialog() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(showEnableBiometricDialog = false)
            _events.emit(LoginEvent.NavigateToHome)
        }
    }
}
