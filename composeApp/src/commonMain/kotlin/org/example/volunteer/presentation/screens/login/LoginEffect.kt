package org.example.volunteer.presentation.screens.login

import org.example.volunteer.core.ui.UiText
import org.example.volunteer.domain.entity.UserRole

sealed interface LoginEffect {
    data class NavigateToHome(val role: UserRole) : LoginEffect
    data class ShowError(val message: UiText) : LoginEffect
    object NavigateToRegistration : LoginEffect
}