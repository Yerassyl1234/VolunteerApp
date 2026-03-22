package org.example.volunteer.presentation.screens.volunteerprofile

import org.example.volunteer.core.ui.UiText

sealed interface VolunteerProfileEffect {
    data class ShowError(val message: UiText) : VolunteerProfileEffect
    object NavigateToLogin : VolunteerProfileEffect
    object NavigateToEditProfile : VolunteerProfileEffect
    object NavigateToNotificationSettings : VolunteerProfileEffect
}