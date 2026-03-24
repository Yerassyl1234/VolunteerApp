package org.example.volunteer.presentation.screens.chatlist

import org.example.volunteer.core.ui.UiText

sealed interface ChatListEffect {
    data class NavigateToChatRoom(val chatId: String) : ChatListEffect
    data class ShowError(val message: UiText) : ChatListEffect
}