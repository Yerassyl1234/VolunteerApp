package org.example.volunteer.presentation.screens.orgprofile

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.example.volunteer.core.common.asNetworkResult
import org.example.volunteer.domain.repository.UserRepository
import org.example.volunteer.domain.usecase.LogoutUseCase
import org.example.volunteer.presentation.BaseViewModel

class OrgProfileViewModel(
    private val userRepository: UserRepository,
    private val logoutUseCase: LogoutUseCase,
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
        userRepository.getOrganizerProfile("me")
            .asNetworkResult()
            .onEach { result ->
                updateState {
                    copy(
                        isLoading = result.isLoading,
                        profile = result.getOrNull()?:profile,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun performLogout() = viewModelScope.launch {
        logoutUseCase()
        sendEffect(OrgProfileEffect.NavigateToLogin)
    }
}