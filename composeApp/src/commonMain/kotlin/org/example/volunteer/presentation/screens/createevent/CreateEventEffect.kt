package org.example.volunteer.presentation.screens.createevent

import org.example.volunteer.core.ui.UiText

sealed interface CreateEventEffect {
    data class ShowError(val message: UiText) : CreateEventEffect
    object NavigateBack : CreateEventEffect
    object NavigateToMyEvents : CreateEventEffect
}