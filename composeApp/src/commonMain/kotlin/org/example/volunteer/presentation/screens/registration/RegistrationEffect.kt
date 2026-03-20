package org.example.volunteer.presentation.screens.registration

import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.core.ui.UiText

sealed interface RegistrationEffect {
    data class NavigateToHome(val role: UserRole) : RegistrationEffect
    data object NavigateToLogin : RegistrationEffect
    data class ShowError(val message: UiText) : RegistrationEffect
}