package org.example.volunteer.presentation.screens.myevents

sealed interface MyEventsAction {
    data object LoadInitialData : MyEventsAction
    data class CancelApplication(
        val applicationId: String,
        val eventId: String
    ) : MyEventsAction
    data class NavigateToDetail(val eventId: String) : MyEventsAction
}