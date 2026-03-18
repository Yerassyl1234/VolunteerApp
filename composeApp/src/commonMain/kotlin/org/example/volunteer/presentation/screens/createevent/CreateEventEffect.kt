package org.example.volunteer.presentation.screens.createevent

import org.example.volunteer.core.ui.UiText

sealed interface CreateEventEffect {
    data object NavigateBack : CreateEventEffect
    data object NavigateToMyEvents : CreateEventEffect
    data class ShowError(val message: UiText) : CreateEventEffect
}