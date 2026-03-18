package org.example.volunteer.presentation.screens.auth.registration

import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.core.ui.UiText

sealed interface RegistrationEffect {
    data object NavigateToHome : RegistrationEffect
    data class NavigateToLogin(val role: UserRole) : RegistrationEffect
    data class ShowError(val message: UiText) : RegistrationEffect
}