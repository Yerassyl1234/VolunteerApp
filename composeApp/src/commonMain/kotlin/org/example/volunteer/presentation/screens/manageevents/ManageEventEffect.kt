package org.example.volunteer.presentation.screens.manageevents

import org.example.volunteer.core.ui.UiText

sealed interface ManageEventEffect {
    data class OpenChat(val chatId: String) : ManageEventEffect
    data class ShowError(val message: UiText) : ManageEventEffect
    object NavigateToArchive : ManageEventEffect
}