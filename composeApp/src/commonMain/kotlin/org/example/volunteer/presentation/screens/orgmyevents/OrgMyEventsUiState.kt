package org.example.volunteer.presentation.screens.orgmyevents

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.ui.UiText
import org.example.volunteer.domain.entity.Event

@Immutable
data class OrgMyEventsUiState(
    val activeEvents: List<Event> = emptyList(),
    val archiveEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
) {
    val showEmpty get() = !isLoading && activeEvents.isEmpty() && error == null
    val showError get() = error != null && !isLoading
}