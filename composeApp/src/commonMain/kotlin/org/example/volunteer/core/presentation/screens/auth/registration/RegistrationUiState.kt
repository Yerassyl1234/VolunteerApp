package org.example.volunteer.core.presentation.screens.auth.registration

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.domain.entity.UserRole


@Immutable
data class RegistrationUiState(
    val selectedRole: UserRole = UserRole.VOLUNTEER,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
) {
    val isValid
        get() = name.isNotBlank()
                && email.contains("@")
                && password.length >= 6
    val canSubmit get() = isValid && !isLoading
}
