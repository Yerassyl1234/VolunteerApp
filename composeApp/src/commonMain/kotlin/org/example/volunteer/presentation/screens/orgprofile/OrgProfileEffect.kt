package org.example.volunteer.presentation.screens.orgprofile

import org.example.volunteer.core.ui.UiText

sealed interface OrgProfileEffect {
    data class ShowError(val message: UiText) : OrgProfileEffect
    object NavigateToLogin : OrgProfileEffect
    object NavigateToEditProfile : OrgProfileEffect
    object NavigateToNotificationSettings : OrgProfileEffect
}