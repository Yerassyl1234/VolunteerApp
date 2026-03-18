package org.example.volunteer.domain.entity

data class Message(
    val id: String,
    val chatId: String,
    val senderId: String,
    val senderName: String,
    val text: String,
    val timestampMs: Long,
    val isRead: Boolean
)
