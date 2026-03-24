package org.example.volunteer.presentation.screens.registration

import org.example.volunteer.domain.entity.UserRole

sealed interface RegistrationAction {
    data class InputUserName(val name: String) : RegistrationAction
    data class InputEmail(val email: String) : RegistrationAction
    data class InputPassword(val password: String) : RegistrationAction
    data class SelectRole(val role: UserRole) : RegistrationAction
    object RegistrationButton : RegistrationAction
    object SwitchToLogin : RegistrationAction
}