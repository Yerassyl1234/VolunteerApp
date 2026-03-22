package org.example.volunteer.presentation.screens.orgprofile

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.ui.UiText
import org.example.volunteer.domain.entity.OrganizerProfile

@Immutable
data class OrgProfileUiState(
    val profile: OrganizerProfile? = null,
    val isLoading: Boolean = false,
    val error: UiText? = null,
) {
    val showLoading get() = isLoading && profile == null
    val showError get() = error != null && profile == null
}