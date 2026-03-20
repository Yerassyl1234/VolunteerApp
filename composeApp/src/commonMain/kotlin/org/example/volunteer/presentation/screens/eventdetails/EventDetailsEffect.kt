package org.example.volunteer.presentation.screens.eventdetails

import org.example.volunteer.core.ui.UiText

sealed interface EventDetailsEffect {
    data class OpenChat(val chatId: String) : EventDetailsEffect
    data class ShowError(val message: UiText) : EventDetailsEffect
    data object ApplicationSent : EventDetailsEffect
    data object CancellationSuccess : EventDetailsEffect
}