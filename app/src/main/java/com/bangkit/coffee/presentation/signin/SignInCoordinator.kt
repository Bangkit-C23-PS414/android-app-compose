package com.bangkit.coffee.presentation.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.coffee.presentation.signin.components.SignInForm

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class SignInCoordinator(
    val viewModel: SignInViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun setPasswordVisibility(visibility: Boolean) {
        viewModel.setPasswordVisibility(visibility)
    }

    fun signIn(formData: SignInForm) {
        viewModel.signIn(formData)
    }
}

@Composable
fun rememberSignInCoordinator(
    viewModel: SignInViewModel = hiltViewModel()
): SignInCoordinator {
    return remember(viewModel) {
        SignInCoordinator(
            viewModel = viewModel
        )
    }
}