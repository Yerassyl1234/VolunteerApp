package org.example.volunteer.presentation.screens.myevents

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.ui.UiText
import org.example.volunteer.domain.entity.Event

@Immutable
data class MyEventsUiState(
    val upcomingEvents: List<Event> = emptyList(),
    val archiveEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
) {
    val showEmpty get() = !isLoading && upcomingEvents.isEmpty() && error == null
    val showError get() = error != null && !isLoading
}