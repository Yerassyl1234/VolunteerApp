package org.example.volunteer.core.presentation.screens.main

sealed interface MainEventEffect {
    data class ShowError(val message: UiText) : MainEventEffect
    data class OpenDetail(val eventId: String) : MainEventEffect
}