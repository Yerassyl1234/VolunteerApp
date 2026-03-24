package org.example.volunteer.presentation.screens.volunteerprofile

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.example.volunteer.core.common.asNetworkResult
import org.example.volunteer.domain.repository.UserRepository
import org.example.volunteer.domain.usecase.LogoutUseCase
import org.example.volunteer.presentation.BaseViewModel

class VolunteerProfileViewModel(
    private val userRepository: UserRepository,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<VolunteerProfileUiState, VolunteerProfileAction, VolunteerProfileEffect>(
    initialState = VolunteerProfileUiState()
) {
    init {
        loadProfile()
    }

    override fun onIntent(intent: VolunteerProfileAction) {
        when (intent) {
            VolunteerProfileAction.EditProfile -> sendEffect(VolunteerProfileEffect.NavigateToEditProfile)
            VolunteerProfileAction.Logout -> performLogout()
            VolunteerProfileAction.NotificationSettings -> sendEffect(VolunteerProfileEffect.NavigateToNotificationSettings)
            is VolunteerProfileAction.SetAvatar -> updateAvatar(intent.image)
        }
    }

    private fun loadProfile() {
        userRepository.getVolunteerProfile("me")
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
        sendEffect(VolunteerProfileEffect.NavigateToLogin)
    }

    private fun updateAvatar(imageUrl: String) {
        // TODO
        updateState { copy(profile = profile?.copy(avatarUrl = imageUrl)) }
    }
}