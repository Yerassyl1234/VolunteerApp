package org.example.volunteer.presentation.screens.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
) {
    val isValid
        get() = email.isNotBlank()
                && password.isNotBlank()
                && email.contains("@")
                && password.length >= 6
    val canSubmit get() = isValid && !isLoading
}
