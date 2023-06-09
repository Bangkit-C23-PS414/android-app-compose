package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.app.LocalRecoffeeryAppActions

@Composable
fun ImageDetectionDetailRoute(
    coordinator: ImageDetectionDetailCoordinator = rememberImageDetectionDetailCoordinator(),
    navigateUp: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberImageDetectionDetailActions(coordinator, navigateUp)

    // Handle events
    val appActions = LocalRecoffeeryAppActions.current
    uiState.message?.let { event ->
        LaunchedEffect(event) {
            event.getContentIfNotHandled()?.let { message -> appActions.showToast(message) }
        }
    }

    // UI Rendering
    ImageDetectionDetailScreen(uiState, actions)
}


@Composable
fun rememberImageDetectionDetailActions(
    coordinator: ImageDetectionDetailCoordinator,
    navigateUp: () -> Unit,
): ImageDetectionDetailActions {
    return remember(coordinator) {
        ImageDetectionDetailActions(
            navigateUp = navigateUp,
            refresh = coordinator::refresh
        )
    }
}