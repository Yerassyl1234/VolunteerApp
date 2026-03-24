package org.example.volunteer.presentation.screens.manageevents

sealed interface ManageEventAction {
    data class Load(val eventId: String) : ManageEventAction
    data class Accept(val applicationId: String) : ManageEventAction
    data class Reject(val applicationId: String) : ManageEventAction
    data class OpenChat(val chatId: String) : ManageEventAction
    object ArchiveEvent : ManageEventAction
}