package org.example.volunteer.core.presentation.screens.auth.registration

import org.example.volunteer.core.domain.entity.UserRole

sealed interface RegistrationEffect {
    data object NavigateToHome : RegistrationEffect
    data class NavigateToLogin(val role: UserRole) : RegistrationEffect
    data class ShowError(val message: UiText) : RegistrationEffect
}