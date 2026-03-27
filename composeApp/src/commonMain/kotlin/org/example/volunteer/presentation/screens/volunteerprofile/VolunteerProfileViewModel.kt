package org.example.volunteer.presentation.screens.volunteerprofile

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.example.volunteer.core.common.asNetworkResult
import org.example.volunteer.core.common.toUiText
import org.example.volunteer.domain.repository.SettingsRepository
import org.example.volunteer.domain.repository.UserRepository
import org.example.volunteer.domain.usecase.LogoutUseCase
import org.example.volunteer.presentation.BaseViewModel

class VolunteerProfileViewModel(
    private val userRepository: UserRepository,
    private val logoutUseCase: LogoutUseCase,
    private val settingsRepository: SettingsRepository,
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
        viewModelScope.launch {
            val userId = settingsRepository.getUserId()
            println("DEBUG_PROFILE: userId=$userId")
            if (userId == null) {
                println("DEBUG_PROFILE: userId is null!")
                return@launch
            }
            try {
                userRepository.getVolunteerProfile(userId)
                    .asNetworkResult()
                    .onEach { result ->
                        println("DEBUG_PROFILE: result=$result")
                        updateState {
                            copy(
                                isLoading = result.isLoading,
                                profile = result.getOrNull() ?: profile,
                                error = result.exceptionOrNull()?.toUiText()
                            )
                        }
                    }
                    .launchIn(viewModelScope)
            } catch (e: Exception) {
                println("DEBUG_PROFILE: exception=${e.message}")
                e.printStackTrace()
            }
        }
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