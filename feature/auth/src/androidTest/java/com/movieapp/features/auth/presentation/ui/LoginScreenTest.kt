package com.movieapp.features.auth.presentation.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.movieapp.features.auth.presentation.viewmodel.LoginUiState
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun passwordLoginContent_displaysAllFields() {
        composeTestRule.setContent {
            PasswordLoginContent(
                uiState = LoginUiState(),
                onUsernameChange = {},
                onPasswordChange = {},
                onLoginClick = {}
            )
        }

        composeTestRule.onNodeWithText("Bem-vindo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Faça login para continuar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Usuário").assertIsDisplayed()
        composeTestRule.onNodeWithText("Senha").assertIsDisplayed()
        composeTestRule.onNodeWithText("Entrar").assertIsDisplayed()
    }

    @Test
    fun passwordLoginContent_loginButtonDisabledWhenFieldsEmpty() {
        composeTestRule.setContent {
            PasswordLoginContent(
                uiState = LoginUiState(username = "", password = ""),
                onUsernameChange = {},
                onPasswordChange = {},
                onLoginClick = {}
            )
        }

        composeTestRule.onNodeWithText("Entrar").assertIsNotEnabled()
    }

    @Test
    fun passwordLoginContent_loginButtonEnabledWhenFieldsFilled() {
        composeTestRule.setContent {
            PasswordLoginContent(
                uiState = LoginUiState(username = "admin", password = "1234"),
                onUsernameChange = {},
                onPasswordChange = {},
                onLoginClick = {}
            )
        }

        composeTestRule.onNodeWithText("Entrar").assertIsEnabled()
    }

    @Test
    fun passwordLoginContent_showsErrorMessage() {
        composeTestRule.setContent {
            PasswordLoginContent(
                uiState = LoginUiState(
                    username = "wrong",
                    password = "wrong",
                    errorMessage = "Usuário ou senha inválidos"
                ),
                onUsernameChange = {},
                onPasswordChange = {},
                onLoginClick = {}
            )
        }

        composeTestRule.onNodeWithText("Usuário ou senha inválidos").assertIsDisplayed()
    }

    @Test
    fun passwordLoginContent_loginButtonDisabledWhenLoading() {
        composeTestRule.setContent {
            PasswordLoginContent(
                uiState = LoginUiState(
                    username = "admin",
                    password = "1234",
                    isLoading = true
                ),
                onUsernameChange = {},
                onPasswordChange = {},
                onLoginClick = {}
            )
        }

        composeTestRule.onNodeWithTag("login_button").assertIsNotEnabled()
    }

    @Test
    fun passwordLoginContent_callsOnLoginClick() {
        var loginClicked = false

        composeTestRule.setContent {
            PasswordLoginContent(
                uiState = LoginUiState(username = "admin", password = "1234"),
                onUsernameChange = {},
                onPasswordChange = {},
                onLoginClick = { loginClicked = true }
            )
        }

        composeTestRule.onNodeWithText("Entrar").performClick()

        assert(loginClicked) { "Login button click was not triggered" }
    }

    @Test
    fun passwordLoginContent_callsOnUsernameChange() {
        var capturedUsername = ""

        composeTestRule.setContent {
            PasswordLoginContent(
                uiState = LoginUiState(),
                onUsernameChange = { capturedUsername = it },
                onPasswordChange = {},
                onLoginClick = {}
            )
        }

        composeTestRule.onNodeWithText("Usuário").performTextInput("admin")

        assert(capturedUsername == "admin") { "Expected 'admin' but got '$capturedUsername'" }
    }

    @Test
    fun biometricLoginContent_displaysCorrectElements() {
        composeTestRule.setContent {
            BiometricLoginContent(
                onBiometricSuccess = {},
                onSwitchToPassword = {}
            )
        }

        composeTestRule.onNodeWithText("Bem-vindo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Toque para autenticar com biometria").assertIsDisplayed()
        composeTestRule.onNodeWithText("Autenticar com Biometria").assertIsDisplayed()
        composeTestRule.onNodeWithText("Entrar com Senha").assertIsDisplayed()
    }

    @Test
    fun biometricLoginContent_switchToPasswordCallsCallback() {
        var switchClicked = false

        composeTestRule.setContent {
            BiometricLoginContent(
                onBiometricSuccess = {},
                onSwitchToPassword = { switchClicked = true }
            )
        }

        composeTestRule.onNodeWithText("Entrar com Senha").performClick()

        assert(switchClicked) { "Switch to password click was not triggered" }
    }

    @Test
    fun enableBiometricDialog_displaysCorrectly() {
        composeTestRule.setContent {
            EnableBiometricDialog(
                onEnable = {},
                onDismiss = {}
            )
        }

        composeTestRule.onNodeWithText("Ativar Biometria").assertIsDisplayed()
        composeTestRule.onNodeWithText("Deseja ativar a autenticação biométrica para os próximos acessos?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sim").assertIsDisplayed()
        composeTestRule.onNodeWithText("Não").assertIsDisplayed()
    }

    @Test
    fun enableBiometricDialog_clickYesCallsOnEnable() {
        var enableCalled = false

        composeTestRule.setContent {
            EnableBiometricDialog(
                onEnable = { enableCalled = true },
                onDismiss = {}
            )
        }

        composeTestRule.onNodeWithText("Sim").performClick()

        assert(enableCalled) { "Enable biometric was not called" }
    }

    @Test
    fun enableBiometricDialog_clickNoCallsOnDismiss() {
        var dismissed = false

        composeTestRule.setContent {
            EnableBiometricDialog(
                onEnable = {},
                onDismiss = { dismissed = true }
            )
        }

        composeTestRule.onNodeWithText("Não").performClick()

        assert(dismissed) { "Dismiss was not called" }
    }
}
