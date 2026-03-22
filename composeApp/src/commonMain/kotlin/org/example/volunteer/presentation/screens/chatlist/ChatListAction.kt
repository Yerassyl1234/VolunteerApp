package org.example.volunteer.presentation.screens.chatlist

sealed interface ChatListAction {
    data class OpenChat(val chatId: String) : ChatListAction
    object LoadInitialData : ChatListAction
}