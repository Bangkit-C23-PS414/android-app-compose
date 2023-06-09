package com.bangkit.coffee.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.bangkit.coffee.data.repository.SessionRepository
import com.bangkit.coffee.navigation.Screen
import com.bangkit.coffee.shared.util.tryParseJWT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoffeeryAppViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<RecoffeeryAppState> =
        MutableStateFlow(RecoffeeryAppState())
    val stateFlow: StateFlow<RecoffeeryAppState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            sessionRepository.flow().collect { token ->
                _stateFlow.update {
                    it.copy(
                        token = token,
                        isValid = tryParseJWT(token)?.isExpired(30) == false
                    )
                }
            }
        }
    }

    fun onBackStackEntryChanged(navBackStackEntry: NavBackStackEntry) {
        _stateFlow.update {
            it.copy(
                currentDestination = navBackStackEntry.destination,
                currentRoute = navBackStackEntry.destination.route,
                shouldShowTopAppBar = navBackStackEntry.destination.route in Screen.Manifest.topBarRoutes,
                shouldShowNavigationBar = navBackStackEntry.destination.route in Screen.Manifest.bottomBarRoutes,
                topBarTitle = getTitle(navBackStackEntry.destination.route)
            )
        }
    }

    private fun getTitle(route: String?): String {
        return when(route) {
            "home" -> "Home"
            "history" -> "History"
            else -> "No Title"
        }
    }

    // Mutable/SharedFlow of String resource reference Event
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    // Post in background thread
    fun showToast(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

}