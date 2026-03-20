package org.example.volunteer.presentation.screens.eventdetails

sealed interface EventDetailsAction {
    data class Load(val eventId: String) : EventDetailsAction
    data object Apply : EventDetailsAction
    data object CancelApplication : EventDetailsAction
    data object OpenChat : EventDetailsAction
}