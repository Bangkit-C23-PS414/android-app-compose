package com.bangkit.coffee.presentation.forgotpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.app.LocalRecoffeeryAppActions

@Composable
fun ForgotPasswordRoute(
    coordinator: ForgotPasswordCoordinator = rememberForgotPasswordCoordinator(),
    navigateToVerifyOTP: (String) -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberForgotPasswordActions(coordinator, navigateToVerifyOTP)

    // Handle events
    val appActions = LocalRecoffeeryAppActions.current
    uiState.message?.let { event ->
        LaunchedEffect(event) {
            event.getContentIfNotHandled()?.let { message ->
                appActions.showToast(message)
            }
        }
    }
    uiState.emailInput?.let {
        LaunchedEffect(it) {
            it.getContentIfNotHandled()?.let { email ->
                actions.navigateToVerifyOTP(email)
            }
        }
    }

    // UI Rendering
    ForgotPasswordScreen(uiState, actions)
}


@Composable
fun rememberForgotPasswordActions(
    coordinator: ForgotPasswordCoordinator,
    navigateToVerifyOTP: (String) -> Unit
): ForgotPasswordActions {
    return remember(coordinator) {
        ForgotPasswordActions(
            forgotPassword = coordinator::forgotPassword,
            navigateToVerifyOTP = navigateToVerifyOTP
        )
    }
}