package org.example.volunteer.presentation.screens.chatroom

sealed interface ChatRoomAction {
    data class Load(val chatId: String) : ChatRoomAction
    data class MessageChanged(val text: String) : ChatRoomAction
    object SendMessage : ChatRoomAction
}