package com.bangkit.coffee.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.app.LocalRecoffeeryAppActions

@Composable
fun CameraRoute(
    coordinator: CameraCoordinator = rememberCameraCoordinator(),
    navigateUp: () -> Unit = {},
    navigateToDetail: () -> Unit = {},
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberCameraActions(coordinator, navigateUp, navigateToDetail)

    // Handle events
    val appActions = LocalRecoffeeryAppActions.current
    if (uiState.uploaded) {
        LaunchedEffect(Unit) { actions.navigateToDetail() }
    }
    uiState.message?.let { event ->
        LaunchedEffect(event) {
            event.getContentIfNotHandled()?.let { message -> appActions.showToast(message) }
        }
    }

    // UI Rendering
    CameraScreen(uiState, actions)
}


@Composable
fun rememberCameraActions(
    coordinator: CameraCoordinator,
    navigateUp: () -> Unit,
    navigateToDetail: () -> Unit
): CameraActions {
    return remember(coordinator) {
        CameraActions(
            navigateUp = navigateUp,
            navigateToDetail = navigateToDetail,
            toggleFlash = coordinator::toggleFlash,
            capturing = coordinator::capturing,
            cancelCapturing = coordinator::cancelCapturing,
            setImage = coordinator::setImage,
            clearImage = coordinator::clearImage,
            uploadImage = coordinator::uploadImage,
            toggleLocalClassifier = coordinator::toggleLocalClassifier,
            setLocalClassifierResult = coordinator::setLocalClassifierResult
        )
    }
}