package org.example.volunteer.presentation.screens.eventdetails

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.ui.UiText
import org.example.volunteer.domain.entity.ApplicationStatus
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.entity.VolunteerProfile

enum class ButtonState {
    Apply,
    Pending,
    Accepted,
    Reapply
}

@Immutable
data class EventDetailsUiState(
    val event: Event? = null,
    val applicationId: String? = null,
    val participants: List<VolunteerProfile> = emptyList(),
    val applicationStatus: ApplicationStatus? = null,
    val isLoading: Boolean = false,
    val isApplying: Boolean = false,
    val error: UiText? = null,
) {
    val showLoading get() = isLoading && event == null
    val showError get() = error != null && event == null

    val buttonState
        get() = when (applicationStatus) {
            null -> ButtonState.Apply
            ApplicationStatus.PENDING -> ButtonState.Pending
            ApplicationStatus.ACCEPTED -> ButtonState.Accepted
            ApplicationStatus.REJECTED -> ButtonState.Reapply
        }
    val isButtonEnabled get() = buttonState != ButtonState.Pending && !isApplying
}
