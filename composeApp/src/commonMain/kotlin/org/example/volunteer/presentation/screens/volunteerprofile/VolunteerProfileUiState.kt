package org.example.volunteer.presentation.screens.volunteerprofile

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.ui.UiText
import org.example.volunteer.domain.entity.VolunteerProfile

@Immutable
data class VolunteerProfileUiState(
    val profile: VolunteerProfile? = null,
    val isLoading: Boolean = false,
    val error: UiText? = null,
) {
    val showLoading get() = isLoading && profile == null
    val showError get() = error != null && profile == null
}