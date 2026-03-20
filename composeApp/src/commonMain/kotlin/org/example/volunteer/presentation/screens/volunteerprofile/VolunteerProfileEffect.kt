package org.example.volunteer.presentation.screens.volunteerprofile

import org.example.volunteer.core.ui.UiText

sealed interface VolunteerProfileEffect {
    data object NavigateToLogin : VolunteerProfileEffect
    data object NavigateToEditProfile : VolunteerProfileEffect
    data object NavigateToNotificationSettings : VolunteerProfileEffect
    data class ShowError(val message: UiText) : VolunteerProfileEffect
}