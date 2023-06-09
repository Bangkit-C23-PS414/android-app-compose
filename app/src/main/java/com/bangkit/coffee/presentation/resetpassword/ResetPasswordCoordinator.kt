package com.bangkit.coffee.presentation.resetpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class ResetPasswordCoordinator(
    val viewModel: ResetPasswordViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun resetPassword(newPassword: String) {
        viewModel.resetPassword(newPassword)
    }
}

@Composable
fun rememberResetPasswordCoordinator(
    viewModel: ResetPasswordViewModel = hiltViewModel()
): ResetPasswordCoordinator {
    return remember(viewModel) {
        ResetPasswordCoordinator(
            viewModel = viewModel
        )
    }
}