package org.example.volunteer.presentation.screens.aicreate

import org.example.volunteer.core.ui.UiText

sealed interface AICreateEffect {
    object NavigateToMyEvents : AICreateEffect
    data class ShowError(val message: UiText) : AICreateEffect
}