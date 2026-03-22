package org.example.volunteer.presentation.screens.orgprofile

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.volunteer.domain.repository.UserRepository
import org.example.volunteer.domain.usecase.LogoutUseCase
import org.example.volunteer.presentation.BaseViewModel

class OrgProfileViewModel(
    private val userRepository: UserRepository,
    private val logout: LogoutUseCase,
) : BaseViewModel<OrgProfileUiState, OrgProfileAction, OrgProfileEffect>(
    initialState = OrgProfileUiState()
) {
    init {
        loadProfile()
    }

    override fun onIntent(intent: OrgProfileAction) {
        when (intent) {
            OrgProfileAction.Logout -> performLogout()
            OrgProfileAction.EditProfile -> sendEffect(OrgProfileEffect.NavigateToEditProfile)
            OrgProfileAction.NotificationSettings -> sendEffect(OrgProfileEffect.NavigateToNotificationSettings)
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            userRepository.getOrganizerProfile("me").collect { profile ->
                updateState { copy(profile = profile, isLoading = false) }
            }
        }
    }

    private fun performLogout() = viewModelScope.launch {
        logout()
        sendEffect(OrgProfileEffect.NavigateToLogin)
    }
}