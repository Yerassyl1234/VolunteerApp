package org.example.volunteer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String,
)

@Serializable
data class RegisterRequestDto(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
)

@Serializable
data class AuthResponseDto(
    val userId: String,
    val role: String,
    val accessToken: String,
    val refreshToken: String,
)

@Serializable
data class RefreshRequestDto(
    val refreshToken: String,
)

@Serializable
data class TokenPairResponseDto(
    val accessToken: String,
    val refreshToken: String,
)

@Serializable
data class MeResponseDto(
    val userId: String,
    val role: String,
)

@Serializable
data class CreateEventRequestDto(
    val title: String,
    val description: String,
    val category: String,
    val locationName: String,
    val locationAddress: String,
    val latitude: Double,
    val longitude: Double,
    val dateMs: Long,
    val durationHours: Int,
    val totalSlots: Int,
    val isFree: Boolean,
    val price: Double? = null,
    val coverUrl: String? = null,
)

@Serializable
data class EventResponseDto(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val locationName: String,
    val locationAddress: String,
    val latitude: Double,
    val longitude: Double,
    val dateMs: Long,
    val durationHours: Int,
    val totalSlots: Int,
    val takenSlots: Int,
    val organizerId: String,
    val organizerName: String,
    val organizerAvatarUrl: String? = null,
    val isOrganizerVerified: Boolean,
    val coverUrl: String? = null,
    val isUrgent: Boolean,
    val isFree: Boolean,
    val price: Double? = null,
    val status: String,
)

@Serializable
data class ApplicationResponseDto(
    val id: String,
    val eventId: String,
    val volunteerId: String,
    val volunteerName: String,
    val volunteerAvatarUrl: String? = null,
    val eventsAttended: Int,
    val status: String,
)

@Serializable
data class ApplicationShortResponseDto(
    val id: String,
    val eventId: String,
    val volunteerId: String,
    val status: String,
)

@Serializable
data class ChatResponseDto(
    val id: String,
    val eventId: String? = null,
    val eventTitle: String? = null,
    val participantName: String,
    val participantAvatarUrl: String? = null,
    val lastMessage: String? = null,
    val lastMessageMs: Long? = null,
    val unreadCount: Int,
    val type: String,
)

@Serializable
data class MessageResponseDto(
    val id: String,
    val chatId: String,
    val senderId: String,
    val senderName: String,
    val text: String,
    val timestampMs: Long,
    val isRead: Boolean,
)

@Serializable
data class SendMessageRequestDto(
    val text: String,
)

@Serializable
data class VolunteerProfileResponseDto(
    val id: String,
    val name: String,
    val avatarUrl: String? = null,
    val email: String,
    val eventsAttended: Int,
    val hoursVolunteered: Int,
    val badges: List<BadgeResponseDto> = emptyList(),
)

@Serializable
data class BadgeResponseDto(
    val id: String,
    val title: String,
    val iconUrl: String? = null,
    val isUnlocked: Boolean,
)

@Serializable
data class OrganizerProfileResponseDto(
    val id: String,
    val name: String,
    val avatarUrl: String? = null,
    val email: String,
    val isVerified: Boolean,
    val eventsCreated: Int,
    val totalVolunteers: Int,
)

@Serializable
data class UpdateProfileRequestDto(
    val name: String? = null,
    val avatarUrl: String? = null,
)

@Serializable
data class AIGenerateRequestDto(
    val prompt: String,
)

@Serializable
data class EventDraftResponseDto(
    val title: String,
    val description: String,
    val category: String,
    val locationName: String,
    val dateMs: Long,
    val durationHours: Int,
    val totalSlots: Int,
    val isFree: Boolean,
)

@Serializable
data class FCMTokenRequestDto(
    val token: String,
)