package org.example.volunteer.presentation.screens.eventdetails

sealed interface EventDetailsAction {
    data class Load(val eventId: String) : EventDetailsAction
    object Apply : EventDetailsAction
    object CancelApplication : EventDetailsAction
    object OpenChat : EventDetailsAction
}