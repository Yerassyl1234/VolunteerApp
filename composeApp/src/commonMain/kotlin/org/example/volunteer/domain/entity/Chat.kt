package org.example.volunteer.domain.entity

data class Chat(
    val id: String,
    val eventId: String?,
    val eventTitle: String?,
    val participantName: String,
    val participantAvatarUrl: String?,
    val lastMessage: String?,
    val lastMessageMs: Long?,
    val unreadCount: Int
)
