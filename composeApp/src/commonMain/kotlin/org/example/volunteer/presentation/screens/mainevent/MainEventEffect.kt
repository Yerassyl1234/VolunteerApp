package org.example.volunteer.presentation.screens.mainevent

import org.example.volunteer.core.ui.UiText

sealed interface MainEventEffect {
    data class ShowError(val message: UiText) : MainEventEffect
    data class OpenDetail(val eventId: String) : MainEventEffect
}