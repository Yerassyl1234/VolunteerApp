package org.example.volunteer.presentation.screens.manageevents

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.ui.UiText
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.domain.entity.EventApplication

@Immutable
data class ManageEventUiState(
    val event: Event? = null,
    val pendingApplications: List<EventApplication> = emptyList(),
    val acceptedVolunteers: List<EventApplication> = emptyList(),
    val processingId: String? = null,
    val isLoading: Boolean = false,
    val error: UiText? = null,
) {
    val showEmptyPending get() = !isLoading && pendingApplications.isEmpty()
    val showEmptyAccepted get() = !isLoading && acceptedVolunteers.isEmpty()
}