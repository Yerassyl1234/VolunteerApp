package org.example.volunteer.presentation.screens.chatroom

import org.example.volunteer.core.ui.UiText

sealed interface ChatRoomEffect {
    data class ShowError(val message: UiText) : ChatRoomEffect
    object NavigateBack : ChatRoomEffect
}