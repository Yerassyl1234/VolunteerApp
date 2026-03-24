package org.example.volunteer.presentation.screens.myevents

import org.example.volunteer.core.ui.UiText

sealed interface MyEventsEffect {
    data class NavigateToDetail(val eventId: String) : MyEventsEffect
    data class ShowError(val message: UiText) : MyEventsEffect
    object CancellationSuccess : MyEventsEffect
}