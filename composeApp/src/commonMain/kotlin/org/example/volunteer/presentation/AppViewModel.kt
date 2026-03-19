package org.example.volunteer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.domain.repository.SettingsRepository

class AppViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<AppState>(AppState.Loading)
    val state: StateFlow<AppState> = _state.asStateFlow()

    init {
        checkAuth()
    }

    private fun checkAuth() {
        viewModelScope.launch {
            val token = settingsRepository.getAccessToken()
            if (token.isNullOrBlank()) {
                _state.value = AppState.NotAuthorized
                return@launch
            }
            val role = settingsRepository.getRole().first()
            _state.value = when (role) {
                UserRole.VOLUNTEER -> AppState.Authorized(UserRole.VOLUNTEER)
                UserRole.ORGANIZER -> AppState.Authorized(UserRole.ORGANIZER)
                null -> AppState.NotAuthorized
            }
        }
    }

    fun onAuthSuccess(role: UserRole) {
        _state.value = AppState.Authorized(role)
    }

    fun onLogout() {
        viewModelScope.launch {
            settingsRepository.clear()
            _state.value = AppState.NotAuthorized
        }
    }
}

