package org.example.volunteer.presentation.screens.chatlist

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.volunteer.domain.repository.ChatRepository
import org.example.volunteer.presentation.BaseViewModel

class ChatListViewModel(
    private val chatRepository: ChatRepository,
) : BaseViewModel<ChatListUiState, ChatListAction, ChatListEffect>(
    initialState = ChatListUiState()
) {
    init {
        onIntent(ChatListAction.LoadInitialData)
    }

    override fun onIntent(intent: ChatListAction) {
        when (intent) {
            ChatListAction.LoadInitialData -> load()
            is ChatListAction.OpenChat -> sendEffect(ChatListEffect.NavigateToChatRoom(intent.chatId))
        }
    }

    private fun load() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            chatRepository.getChats().collect { chats ->
                updateState { copy(chats = chats, isLoading = false) }
            }
        }
    }
}
